package p.lodz.pl.nbd.manager.mapper;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBundles;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBundlesTypes;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toEnvelopes;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toEnvelopesTypes;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ShipmentMapper {

    public static List<Shipment> toShipments(List<ShipmentByStartDate> docs) {
        return docs.stream()
                .map(ShipmentMapper::toShipment)
                .toList();
    }

    public static Shipment toShipment(ShipmentByStartDate doc) {
        var shipment = Shipment.builder()
                .id(doc.getId())
                .bundles(toBundles(doc.getBundles()))
                .envelopes(toEnvelopes(doc.getEnvelopes()))
                .build();
        shipment.recalculateTotalCost();
        shipment.setStartDate(doc.getStartDate());
        shipment.setFinalizationDate(doc.getFinalizationDate());
        return shipment;
    }

    public static ShipmentByStartDate toShipmentByStartDate(Shipment shipment) {
        return ShipmentByStartDate.builder()
                .id(shipment.getId())
                .boxesCost(shipment.getBoxesCost())
                .startDate(shipment.getStartDate())
                .bundles(toBundlesTypes(shipment.getBundles()))
                .envelopes(toEnvelopesTypes(shipment.getEnvelopes()))
                .build();
    }
}
