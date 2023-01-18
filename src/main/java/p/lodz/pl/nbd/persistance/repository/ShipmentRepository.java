package p.lodz.pl.nbd.persistance.repository;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static p.lodz.pl.nbd.persistance.events.Producer.getProducer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TopicExistsException;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;
import p.lodz.pl.nbd.persistance.events.Topics;


@Slf4j
public class ShipmentRepository extends DocumentRepository<ShipmentDocument, UUID> {

    public static final String COLLECTION_NAME = "Shipments";
    public static final String PACZKOMAT_WIKTOR_PAWLAK = "paczkomat-wiktor-pawlak";

    public ShipmentRepository() {
        super(ShipmentDocument.class, COLLECTION_NAME);
        initDbConnection();
    }

    @Override
    public <S extends ShipmentDocument> ShipmentDocument save(final S box) {
        try {
            ProducerRecord<UUID, String> record = new ProducerRecord<>(Topics.CLIENT_TOPIC,
                    box.getId(), PACZKOMAT_WIKTOR_PAWLAK + "-" + LocalDateTime.now());

            log.info("Record info: \n" + record + "\n");

            Future<RecordMetadata> sent = getProducer().send(record);
            sent.get();
        } catch (final ExecutionException e) {
            log.error(String.valueOf(e.getCause()));
            assertThat(e.getCause(), is(instanceOf(TopicExistsException.class)));
        } catch (final InterruptedException e) {
            log.error(String.valueOf(e.getCause()));
        }

        return super.save(box);
    }

    public Optional<BoxDocument> findBoxById(final UUID boxId) {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        var shipmentDoc = shipments.find(eq("boxes._id", boxId)).first();

        if (shipmentDoc == null) {
            return Optional.empty();
        }

        return Optional.of(shipmentDoc.getBoxes().stream().filter(it -> it.getId().equals(boxId)).findFirst().orElseThrow());
    }

    public List<ShipmentDocument> getArchivedShipments() {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        Bson filter = Filters.eq("ongoing", false);
        return shipments.find(filter).into(new ArrayList<>());
    }

    public void archiveShipment(final UUID id) {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        shipments.updateOne(eq("_id", id),
                combine(set("ongoing", false),
                        set("locker.empty", true),
                        set("locker.password", "")));
    }

    public void deleteShipment(final UUID id) {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        shipments.deleteOne(eq("_id", id));
    }
}
