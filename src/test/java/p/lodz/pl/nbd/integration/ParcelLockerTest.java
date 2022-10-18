package p.lodz.pl.nbd.integration;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.model.ParcelLocker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.repository.ShipmentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParcelLockerTest {

    private ParcelLockerFixture fixture;

    private EntityManagerFactory emf;

    private EntityManager em;

    private ShipmentRepository shipmentRepository;

    private ShipmentManager shipmentManager;

    private ParcelLocker parcelLocker;

    @BeforeEach
    void init() {
        if (emf != null) {
            emf.close();
        }
        fixture = new ParcelLockerFixture();
        emf = Persistence.createEntityManagerFactory("postgres");
        em = emf.createEntityManager();
        shipmentRepository = new ShipmentRepository();
        shipmentManager = ShipmentManager.of(shipmentRepository);
        parcelLocker = ParcelLocker.builder()
                .shipmentManager(shipmentManager)
                .build();
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
    void saveShipmentSuccessfully() {
        //given
        Shipment shipment = new Shipment(fixture.fullLockers.get(0), List.of(fixture.bundle));

        //when
        shipmentRepository.save(shipment);

        //then
        Shipment shipmentFromDB = shipmentRepository.findById(shipment.getId()).orElseThrow();
        assertNotNull(shipmentFromDB);
        assertSame(shipmentFromDB, shipment);
    }

    @Test
    @SneakyThrows
    void shouldPolymorphicallyCalculateTotalCost() {
        //given
        Shipment shipment = new Shipment(fixture.fullLockers.get(0), List.of(fixture.bundle, fixture.envelope));
        shipmentRepository.save(shipment);

        //when
        double result = shipmentManager.totalCost(shipment.getId());

        //then
        assertEquals(70, result);
    }

    @Test
    void optimisticLockIsThrownWhenFinalizingShipmentTwice() throws Throwable {
        //given
        Shipment shipment = new Shipment(fixture.fullLockers.get(0), List.of(fixture.bundle));
        shipment.setOngoing(true);

        ShipmentRepository shipmentRepository2 = new ShipmentRepository();
        ShipmentManager shipmentManager2 = ShipmentManager.of(shipmentRepository2);
        shipmentRepository.save(shipment);
        //when
        Shipment operator1 = shipmentRepository.findById(shipment.getId()).orElseThrow();
        Shipment operator2 = shipmentRepository2.findById(shipment.getId()).orElseThrow();

        shipmentManager.finalizeShipment(operator1.getId());

        Executable finalizeShipment = () -> shipmentManager2.finalizeShipment(operator2.getId());

        //then
        assertThrows(OptimisticLockException.class, finalizeShipment);
    }
}























