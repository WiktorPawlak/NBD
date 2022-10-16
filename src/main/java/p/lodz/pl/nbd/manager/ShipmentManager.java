package p.lodz.pl.nbd.manager;

import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Box;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistance.ShipmentRepository;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentManager {

    @Inject
    private ShipmentRepository shipmentsRepository;

    public Shipment getShipment(final UUID shipmentId) {
        return shipmentsRepository.findById(shipmentId).orElseThrow();
    }

    public List<Shipment> getAllShipments() {
        return shipmentsRepository.findAll();
    }

    public List<Shipment> getArchivedShipments() {
        return shipmentsRepository.getArchivedShipments();
    }

    public void addShipment(final Locker locker, final List<Box> boxes) {
        shipmentsRepository.save(new Shipment(locker, boxes));
    }

    public void finalizeShipment(final UUID shipmentId) {
        shipmentsRepository.archiveShipment(shipmentId);
    }
}
