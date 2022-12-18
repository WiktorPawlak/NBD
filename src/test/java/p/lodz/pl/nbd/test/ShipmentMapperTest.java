package p.lodz.pl.nbd.test;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentByStartDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import p.lodz.pl.nbd.BoxesShipmentsFixture;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;

class ShipmentMapperTest {

    private BoxesShipmentsFixture fixture;

    @BeforeEach
    void init() {
        fixture = new BoxesShipmentsFixture();
    }

    @Test
    void shipmentDocumentMapsToShipmentSuccessfully() {
        //given
        ShipmentByStartDate shipmentDoc = fixture.shipmentByStartDate;

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
        ShipmentByStartDate mappedShipmentDoc = toShipmentByStartDate(shipment);

        //then
        Assertions.assertThat(mappedShipmentDoc).usingRecursiveComparison().isEqualTo(shipment);
    }
}
