package p.lodz.pl.nbd.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import lombok.SneakyThrows;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.manager.mapper.ShipmentMapper;
import p.lodz.pl.nbd.model.ParcelLocker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.repository.ShipmentRepository;


class ParcelLockerTest {

    //todo analyze which tests must stay/can be deleted
    //todo write new tests for new CRUD operations
    private ParcelLockerFixture fixture;

    private ShipmentRepository shipmentRepository;

    private ShipmentManager shipmentManager;

    private ParcelLocker parcelLocker;

    @BeforeEach
    void init() {
        fixture = new ParcelLockerFixture();
        shipmentRepository = new ShipmentRepository();
        shipmentManager = ShipmentManager.of(shipmentRepository);
        parcelLocker = ParcelLocker.builder()
                .shipmentManager(shipmentManager)
                .build();
    }

    @Test
    void testtt() throws Throwable {
        parcelLocker.sendPackage(fixture.envelope);
    }

    @Test
    @SneakyThrows
    void sendPackageSuccessfully() {
        //given
        Box envelope = fixture.envelope;

        //when
        parcelLocker.sendPackage(envelope);

        List<Shipment> allShipments = ShipmentMapper.toShipments(shipmentRepository.findAll());
        Shipment sentBoxShipment = allShipments.stream().findFirst().orElseThrow();

        //then
        assertNotNull(sentBoxShipment);
        assertNotNull(sentBoxShipment.getBoxes());
        Box sentBox = sentBoxShipment.getBoxes().get(0);
        assertEquals(sentBox, envelope);
        assertSame(sentBox, envelope);
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

        List<Shipment> allShipments = ShipmentMapper.toShipments(shipmentRepository.findAll());
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

        List<Shipment> allShipments = ShipmentMapper.toShipments(shipmentRepository.findAll());
        Shipment sentBoxShipment = allShipments.stream().findFirst().orElseThrow();
        String password = sentBoxShipment.getLocker().getPassword();

        //when
        parcelLocker.setLockers(fixture.fullLockers);
        Executable receivePackage = () -> parcelLocker.receivePackage(password, sentBoxShipment.getId());

        //when
        assertThrows(Exception.class, receivePackage);
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
    void saveShipmentSuccessfully() {
        //given
        Shipment shipment = new Shipment(UUID.randomUUID(), fixture.fullLockers.get(0), List.of(fixture.bundle));

        //when
        shipmentRepository.save(ShipmentMapper.toShipmentDocument(shipment));

        //then
        Shipment shipmentFromDB = ShipmentMapper.toShipment(shipmentRepository.findById(shipment.getId()).orElseThrow());
        assertNotNull(shipmentFromDB);
        assertSame(shipmentFromDB, shipment);
    }

//    @Test
//    void optimisticLockIsThrownWhenFinalizingShipmentTwice() throws Throwable {
//        //given
//        Shipment shipment = new Shipment(nullfixture.fullLockers.get(0), List.of(fixture.bundle));
//        shipment.setOngoing(true);
//
//        ShipmentRepository shipmentRepository2 = new ShipmentRepository();
//        ShipmentManager shipmentManager2 = ShipmentManager.of(shipmentRepository2);
//        shipmentRepository.save(shipment);
//        //when
//        Shipment operator1 = shipmentRepository.findById(shipment.getId()).orElseThrow();
//        Shipment operator2 = shipmentRepository2.findById(shipment.getId()).orElseThrow();
//
//        shipmentManager.finalizeShipment(operator1.getId());
//
//        Executable finalizeShipment = () -> shipmentManager2.finalizeShipment(operator2.getId());
//
//        //then
//        assertThrows(OptimisticLockException.class, finalizeShipment);
//    } //todo analyze how to test concurrent business operations
}























