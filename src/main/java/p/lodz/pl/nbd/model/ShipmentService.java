package p.lodz.pl.nbd.model;

import java.util.List;
import java.util.UUID;

import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;

public interface ShipmentService {

    List<Shipment> getAllShipments();

    Shipment getShipment(final UUID shipmentId);

    List<Shipment> getArchivedShipments();

    ShipmentDocument addShipment(final Locker locker, final List<Box> boxes);

    Box getBoxById(UUID boxId);

    boolean checkIfBoxWasSent(UUID boxId);

    void finalizeShipment(final UUID shipmentId);

    void removeShipment(final UUID shipmentId);
}
