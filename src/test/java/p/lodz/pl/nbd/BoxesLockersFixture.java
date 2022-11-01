package p.lodz.pl.nbd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.ParcelLocker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;
import p.lodz.pl.nbd.persistance.document.box.BundleDocument;
import p.lodz.pl.nbd.persistance.document.box.EnvelopeDocument;
import p.lodz.pl.nbd.persistance.document.shipment.LockerDocument;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;

public class BoxesLockersFixture {

    public Box bundle = Box.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    Bundle.builder()
                            .fragile(true)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();

    public BoxDocument bundleDoc = BoxDocument.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    BundleDocument.builder()
                            .fragile(true)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();

    public Box envelope = Box.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    Envelope.builder()
                            .priority(1)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();

    public BoxDocument envelopeDoc = BoxDocument.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    EnvelopeDocument.builder()
                            .priority(1)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();
    public ShipmentDocument shipmentDocument = createShipmentDocument();
    public List<Locker> fullLockers = fillLockers();
    public Shipment shipment = createShipment();

    private Shipment createShipment() {
        var shipment = Shipment.builder()
                .id(UUID.randomUUID())
                .locker(fullLockers.get(0))
                .boxes(List.of(envelope, bundle))
                .build();
        shipment.setOngoing(true);
        return shipment;
    }

    private ShipmentDocument createShipmentDocument() {
        var shipmentDoc = ShipmentDocument.builder()
                .id(UUID.randomUUID())
                .locker(LockerDocument.builder()
                        .password(UUID.randomUUID().toString())
                        .empty(false)
                        .build())
                .boxes(List.of(envelopeDoc, bundleDoc))
                .build();
        shipmentDoc.setOngoing(true);
        return shipmentDoc;
    }

    private List<Locker> fillLockers() {
        List<Locker> lockers = new ArrayList<>(ParcelLocker.LOCKER_AMMOUNT);

        for (int i = 0; i < ParcelLocker.LOCKER_AMMOUNT; i++) {
            lockers.add(new Locker());
            lockers.get(i).setEmpty(false);
        }

        return lockers;
    }
}
