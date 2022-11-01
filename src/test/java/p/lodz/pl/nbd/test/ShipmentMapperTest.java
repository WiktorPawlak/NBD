package p.lodz.pl.nbd.test;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentDocument;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import p.lodz.pl.nbd.BoxesLockersFixture;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;

class ShipmentMapperTest {

    private BoxesLockersFixture fixture;

    @BeforeEach
    void init() {
        fixture = new BoxesLockersFixture();
    }

    @Test
    void shipmentDocumentMapsToShipmentSuccessfully() {
        //given
        ShipmentDocument shipmentDoc = fixture.shipmentDocument;

        //when
        Shipment mappedShipment = toShipment(shipmentDoc);

        //then
        Assertions.assertThat(mappedShipment).usingRecursiveComparison().isEqualTo(shipmentDoc);
    }

    @Test
    void shipmentMapsToShipmentDocumentSuccessfully() {
        //given
        Shipment shipment = fixture.shipment;

        //when
        ShipmentDocument mappedShipmentDoc = toShipmentDocument(shipment);

        //then
        Assertions.assertThat(mappedShipmentDoc).usingRecursiveComparison().isEqualTo(shipment);
    }
}
