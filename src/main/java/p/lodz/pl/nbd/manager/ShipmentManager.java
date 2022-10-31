package p.lodz.pl.nbd.manager;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentDocument;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipments;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.repository.ShipmentRepository;


@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentManager {

    private ShipmentRepository shipmentsRepository;

    public List<Shipment> getAllShipments() {
        return toShipments(shipmentsRepository.findAll());
    }

    public Shipment getShipment(final UUID shipmentId) {
        return toShipment(shipmentsRepository.findById(shipmentId).orElseThrow());
    }

    public List<Shipment> getArchivedShipments() {
        return toShipments(shipmentsRepository.getArchivedShipments());
    }

    public void addShipment(final Locker locker, final List<Box> boxes) throws Throwable {
        Shipment shipment = new Shipment(UUID.randomUUID(), locker, boxes);
        shipmentsRepository.save(toShipmentDocument(shipment));
    }

    public void finalizeShipment(final UUID shipmentId) throws Throwable {
        shipmentsRepository.archiveShipment(shipmentId);
    }
}
