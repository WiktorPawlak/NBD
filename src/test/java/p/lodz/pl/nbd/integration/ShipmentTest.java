package p.lodz.pl.nbd.integration;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import p.lodz.pl.nbd.BoxesShipmentsFixture;
import p.lodz.pl.nbd.SmokeTest;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistence.config.CassandraConfig;


class ShipmentTest {

    private BoxesShipmentsFixture fixture;
    private ShipmentManager shipmentManager;

    @BeforeEach
    void init() {
        CassandraConfig.initDb();
        fixture = new BoxesShipmentsFixture();
        shipmentManager = new ShipmentManager();
    }

    @AfterEach
    void clearDb() {
        CassandraConfig.clearShipmentsByStartDate();
    }

    @SmokeTest
    public void getAllShipments() {
        assertNotNull(shipmentManager.getAllShipments());
    }

    @Test
    void addShipmentSuccessfully() {
        //given
        Envelope envelope = fixture.envelope;
        Bundle bundle = fixture.bundle;

        //when
        UUID shipmentId = shipmentManager.addShipment(List.of(bundle), List.of(envelope));
        Shipment newShipment = shipmentManager.getShipment(shipmentId);

        //then
        assertNotNull(newShipment.getBundles());
        assertNotNull(newShipment.getEnvelopes());
        assertNull(newShipment.getFinalizationDate());
        Bundle sentBundle = newShipment.getBundles().get(0);
        Envelope sentEnvelope = newShipment.getEnvelopes().get(0);
        Assertions.assertThat(sentBundle).usingRecursiveComparison().isEqualTo(bundle);
        Assertions.assertThat(sentEnvelope).usingRecursiveComparison().isEqualTo(envelope);
    }

    @Test
    void finalizedShipmentsSuccessfully() {
        //given
        Envelope envelope = fixture.envelope;
        Bundle bundle = fixture.bundle;

        //when
        UUID shipmentId = shipmentManager.addShipment(List.of(bundle), List.of(envelope));
        Shipment newShipment = shipmentManager.getShipment(shipmentId);
        shipmentManager.finalizeShipment(newShipment.getId(), newShipment.getStartDate());

        //then
        var finalizedShipment = shipmentManager.getShipment(shipmentId);
        assertNotNull(finalizedShipment.getFinalizationDate());
    }

    @Test
    void getFinalizedShipmentsSuccessfully() {
        //given
        Envelope envelope = fixture.envelope;
        Bundle bundle = fixture.bundle;

        //when
        UUID shipmentId = shipmentManager.addShipment(List.of(bundle), List.of(envelope));
        Shipment newShipment = shipmentManager.getShipment(shipmentId);
        shipmentManager.finalizeShipment(newShipment.getId(), newShipment.getStartDate());

        //then
        var finalizedShipments = shipmentManager.getFinalizedShipments();
        Assertions.assertThat(finalizedShipments.get(0))
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes("finalizationDate")
                .isEqualTo(newShipment);
        assertNotNull(finalizedShipments.get(0).getFinalizationDate());
    }

    @Test
    void deleteShipmentSuccessfully() {
        //given
        Envelope envelope = fixture.envelope;
        Bundle bundle = fixture.bundle;
        UUID shipmentId = shipmentManager.addShipment(List.of(bundle), List.of(envelope));

        //when
        shipmentManager.removeShipment(shipmentId);

        //then
        assertThrows(NoSuchElementException.class, () -> shipmentManager.getShipment(shipmentId));
    }
}
