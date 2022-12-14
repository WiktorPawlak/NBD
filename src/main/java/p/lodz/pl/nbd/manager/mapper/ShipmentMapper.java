package p.lodz.pl.nbd.manager.mapper;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBoxes;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBoxesDocuments;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistence.document.shipment.LockerDocument;
import p.lodz.pl.nbd.persistence.document.shipment.ShipmentDocument;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ShipmentMapper {

    public static List<Shipment> toShipments(List<ShipmentDocument> docs) {
        return docs.stream()
                .map(ShipmentMapper::toShipment)
                .collect(Collectors.toList());
    }

    public static Shipment toShipment(ShipmentDocument doc) {
        var shipment = Shipment.builder()
                .id(doc.getId())
                .boxes(toBoxes(doc.getBoxes()))
                .locker(toLocker(doc.getLocker()))
                .build();
        shipment.setOngoing(doc.isOngoing());
        return shipment;
    }

    private static Locker toLocker(final LockerDocument locker) {
        return Locker.builder()
                .empty(locker.getEmpty())
                .password(locker.getPassword())
                .build();
    }

    public static ShipmentDocument toShipmentDocument(Shipment shipment) {
        var shipmentDoc = ShipmentDocument.builder()
                .id(shipment.getId())
                .locker(toLockerDocument(shipment.getLocker()))
                .boxes(toBoxesDocuments(shipment.getBoxes()))
                .build();
        shipmentDoc.setOngoing(shipment.isOngoing());
        return shipmentDoc;
    }

    private static LockerDocument toLockerDocument(final Locker locker) {
        return LockerDocument.builder()
                .empty(locker.getEmpty())
                .password(locker.getPassword())
                .build();
    }
}
