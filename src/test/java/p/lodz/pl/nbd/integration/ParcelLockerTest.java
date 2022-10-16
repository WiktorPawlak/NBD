package p.lodz.pl.nbd.integration;


import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.model.Box;
import p.lodz.pl.nbd.model.ParcelLocker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistance.ShipmentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParcelLockerTest {

    private ParcelLockerFixture fixture;

    private ShipmentRepository shipmentRepository;
    private ParcelLocker parcelLocker;

    @BeforeEach
    void init() {
        fixture = new ParcelLockerFixture();
        shipmentRepository = new ShipmentRepository();
        ShipmentManager shipmentManager = ShipmentManager.of(shipmentRepository);
        parcelLocker = ParcelLocker.builder()
                .shipmentManager(shipmentManager)
                .build();
    }

    @Test
    void everyLockerShouldBeEmpty() {
        //given
        //when
        var realAmmount = parcelLocker.countEmptyLockers();

        //then
        assertEquals(ParcelLocker.LOCKER_AMMOUNT, realAmmount);
    }

    @Test
    @SneakyThrows
    void sendPackageSuccessfully() {
        //given
        Box envelope = fixture.envelope;

        //when
        parcelLocker.sendPackage(envelope);

        List<Shipment> allShipments = shipmentRepository.findAll();
        Shipment sentBoxShipment = allShipments.stream().findFirst().orElseThrow();

        //then
        assertNotNull(sentBoxShipment);
        assertNotNull(sentBoxShipment.getBoxes());
        Box sentBox = sentBoxShipment.getBoxes().get(0);
        assertEquals(sentBox, envelope);
        assertSame(sentBox, envelope); //TODO: zapytać się czy tak powinno być; persistence context
    }

    @Test
    void sendPackageThrowsWhenNoEmptyLockers() {
        //given
        parcelLocker.setLockers(fixture.fullLockers);

        //when
        Executable sendPackage = () -> parcelLocker.sendPackage(fixture.bundle);

        //then
        assertThrows(Exception.class, sendPackage);
    }

    @Test
    @SneakyThrows
    void receivePackageSuccessfully() {
        //given
        Box bundle = fixture.bundle;
        parcelLocker.sendPackage(bundle);

        List<Shipment> allShipments = shipmentRepository.findAll();
        Shipment sentBoxShipment = allShipments.stream().findFirst().orElseThrow();
        String password = sentBoxShipment.getLocker().getPassword();

        //when
        parcelLocker.receivePackage(password, sentBoxShipment.getId());

        //then
        Assertions.assertThat(sentBoxShipment)
                .extracting("ongoing", "locker.empty", "locker.password")
                .contains(false, true, "");
    }

    @Test
    @SneakyThrows
    void receivePackageThrowsWhenNoEmptyLockers() {
        //given
        Box bundle = fixture.bundle;
        parcelLocker.sendPackage(bundle);

        List<Shipment> allShipments = shipmentRepository.findAll();
        Shipment sentBoxShipment = allShipments.stream().findFirst().orElseThrow();
        String password = sentBoxShipment.getLocker().getPassword();

        //when
        parcelLocker.setLockers(fixture.fullLockers);
        Executable receivePackage = () -> parcelLocker.receivePackage(password, sentBoxShipment.getId());

        //when
        assertThrows(Exception.class, receivePackage);
    }
}























