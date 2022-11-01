package p.lodz.pl.nbd.persistance.repository;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import p.lodz.pl.nbd.persistance.document.box.BoxDocument;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;


public class ShipmentRepository extends DocumentRepository<ShipmentDocument, UUID> {

    public static final String COLLECTION_NAME = "Shipments";

    public ShipmentRepository() {
        super(ShipmentDocument.class, COLLECTION_NAME);
        initDbConnection();
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

    public void archiveShipment(UUID id) {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        shipments.updateOne(eq("_id", id),
                combine(set("ongoing", false),
                        set("locker.empty", true),
                        set("locker.password", "")));
    }

    public void deleteShipment(UUID id) {
        MongoCollection<ShipmentDocument> shipments = getMongoRepository().getCollection(COLLECTION_NAME, ShipmentDocument.class);
        shipments.deleteOne(eq("_id", id));
    }
}
