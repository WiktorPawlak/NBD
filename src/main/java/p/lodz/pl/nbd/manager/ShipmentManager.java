package p.lodz.pl.nbd.manager;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBox;
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
import p.lodz.pl.nbd.model.ShipmentService;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;
import p.lodz.pl.nbd.persistance.repository.ShipmentRepository;


@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentManager implements ShipmentService {

    private ShipmentRepository shipmentsRepository;

    public List<Shipment> getAllShipments() {
        return toShipments(shipmentsRepository.findAll());
    }

    public Shipment getShipment(final UUID shipmentId) { //cache shipment
        return toShipment(shipmentsRepository.findById(shipmentId).orElseThrow());
    }

    public List<Shipment> getArchivedShipments() {
        return toShipments(shipmentsRepository.getArchivedShipments());
    }

    public ShipmentDocument addShipment(final Locker locker, final List<Box> boxes) {
        Shipment shipment = new Shipment(UUID.randomUUID(), locker, boxes);
        shipment.setOngoing(true);
        return shipmentsRepository.save(toShipmentDocument(shipment));
    }

    public Box getBoxById(UUID boxId) { //cache box
        return toBox(shipmentsRepository.findBoxById(boxId).orElseThrow());
    }

    public boolean checkIfBoxWasSent(UUID boxId) {
        return shipmentsRepository.findBoxById(boxId).isPresent();
    }

    public void finalizeShipment(final UUID shipmentId) { //update cache
        shipmentsRepository.archiveShipment(shipmentId);
    }

    public void removeShipment(final UUID shipmentId) { //invalidate cache
        shipmentsRepository.deleteShipment(shipmentId);
    }
}
