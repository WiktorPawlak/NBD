package p.lodz.pl.nbd.manager.mapper;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBoxesDocuments;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistance.document.shipment.LockerDocument;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ShipmentMapper {

    public static List<Shipment> toShipments(List<ShipmentDocument> docs) {
        return docs.stream()
                .map(ShipmentMapper::toShipment)
                .collect(Collectors.toList());
    }

    public static Shipment toShipment(ShipmentDocument doc) {
        return Shipment.builder()
                //todo: map database entity to domain entity
                .build();
    }

    public static ShipmentDocument toShipmentDocument(Shipment shipment) {
        return ShipmentDocument.builder()
                .locker(toLockerDocument(shipment.getLocker()))
                .boxes(toBoxesDocuments(shipment.getBoxes()))
                .build();
    }

    private static LockerDocument toLockerDocument(final Locker locker) {
        return LockerDocument.builder()
                //todo: map domain entity to database entity
                .build();
    }
}
