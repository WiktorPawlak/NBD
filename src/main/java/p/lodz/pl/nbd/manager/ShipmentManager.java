package p.lodz.pl.nbd.manager;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentDocument;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistence.document.shipment.ShipmentDao;
import p.lodz.pl.nbd.persistence.repository.CassandraConfig;
import p.lodz.pl.nbd.persistence.repository.InboxIdentifiers;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ShipmentManager {

    private final ShipmentDao shipmentsRepository = CassandraConfig.inboxMapper.shipmentDao(InboxIdentifiers.NBD_INBOX);

//    public List<Shipment> getAllShipments() {
//        return toShipments(shipmentsRepository.findAll());
//    }

    public Shipment getShipment(final UUID shipmentId) {
        return toShipment(shipmentsRepository.findById(shipmentId));
    }

//    public List<Shipment> getArchivedShipments() {
//        return toShipments(shipmentsRepository.getArchivedShipments());
//    }

    public UUID addShipment(final Locker locker, final List<Box> boxes) {
        Shipment shipment = new Shipment(UUID.randomUUID(), locker, boxes);
        shipment.setOngoing(true);
        return shipmentsRepository.create(toShipmentDocument(shipment)).getId();
    }

//    public Box getBoxById(UUID boxId) {
//        return toBox(shipmentsRepository.findBoxById(boxId).orElseThrow());
//    }

//    public void finalizeShipment(final UUID shipmentId) {
//        shipmentsRepository.archiveShipment(shipmentId);
//    }

    public void removeShipment(final UUID shipmentId) {
        shipmentsRepository.delete(shipmentId);
    }
}
