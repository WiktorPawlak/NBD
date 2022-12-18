package p.lodz.pl.nbd;

import java.util.List;
import java.util.UUID;

import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;

public class BoxesShipmentsFixture {

    public Bundle bundle = Bundle.lombokBuilder()
            .fragile(true)
            .weight(10)
            .length(2)
            .height(2)
            .width(2)
            .lombokBuild();

    public BundleUdt bundleDoc = BundleUdt.builder()
            .fragile(true)
            .weight(10)
            .length(2)
            .height(2)
            .width(2)
            .build();

    public Envelope envelope = Envelope.lombokBuilder()
            .priority(1)
            .weight(10)
            .length(2)
            .height(2)
            .width(2)
            .lombokBuild();

    public EnvelopeUdt envelopeDoc = EnvelopeUdt.builder()
            .priority(1)
            .weight(10)
            .length(2)
            .height(2)
            .width(2)
            .build();

    public Shipment shipment = createShipment();

    public ShipmentByStartDate shipmentByStartDate = createShipmentByStartDate();

    private Shipment createShipment() {
        return Shipment.builder()
                .id(UUID.randomUUID())
                .bundles(List.of(bundle))
                .envelopes(List.of(envelope))
                .build();
    }

    private ShipmentByStartDate createShipmentByStartDate() {
        return ShipmentByStartDate.builder()
                .id(UUID.randomUUID())
                .bundles(List.of(bundleDoc))
                .envelopes(List.of(envelopeDoc))
                .boxesCost(shipment.getBoxesCost())
                .startDate(shipment.getStartDate())
                .build();
    }
}
