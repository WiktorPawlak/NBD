package p.lodz.pl.nbd.integration;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import p.lodz.pl.nbd.BoxesShipmentsFixture;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.persistence.config.CassandraConfig;
import p.lodz.pl.nbd.persistence.config.InboxIdentifiers;
import p.lodz.pl.nbd.persistence.repository.ShipmentDao;


class ParcelLockerTest {
    private final ShipmentDao shipmentRepository = CassandraConfig.SHIPMENT_MAPPER.shipmentDao(InboxIdentifiers.NBD_INBOX);
    private BoxesShipmentsFixture fixture;
    private ShipmentManager shipmentManager;

    @BeforeEach
    void init() {
        fixture = new BoxesShipmentsFixture();
        shipmentManager = new ShipmentManager();
    }

    @AfterEach
    void clearDb() {
//        shipmentRepository.getMongoClient().getDatabase("NBD-Z2-DB").drop();
//        shipmentRepository.close();
    }

//    @SmokeTest
//    public void getArchivedShipments() {
//        assertNotNull(shipmentManager.getArchivedShipments());
//    }
//
//    @SmokeTest
//    public void getAllShipments() {
//        assertNotNull(shipmentManager.getAllShipments());
//    }

//    @Test
//    @SneakyThrows
//    void sendPackageSuccessfully() {
//        //given
//        Box envelope = fixture.envelope;
//
//        //when
//        UUID shipmentId = parcelLocker.sendPackage(envelope);
//
//        Shipment envelopesShipment = ShipmentMapper.toShipment(
//                shipmentRepository.findById(shipmentId));
//
//        //then
//        assertNotNull(envelopesShipment);
//        assertNotNull(envelopesShipment.getBoxes());
//        Box sentBox = envelopesShipment.getBoxes().get(0);
//        assertEquals(sentBox, envelope);
//    }
//
//    @Test
//    void sendPackageThrowsWhenNoEmptyLockers() {
//        //given
//        parcelLocker.setLockers(fixture.fullLockers);
//
//        //when
//        Executable sendPackage = () -> parcelLocker.sendPackage(fixture.bundle);
//
//        //then
//        assertThrows(Exception.class, sendPackage);
//    }
//
//    @Test
//    @SneakyThrows
//    void receivePackageSuccessfully() {
//        //given
//        Box bundle = fixture.bundle;
//        UUID bundleShipmentId = parcelLocker.sendPackage(bundle);
//        String password = shipmentManager.getShipment(bundleShipmentId).getLocker().getPassword();
//
//        //when
//        parcelLocker.receivePackage(password, bundleShipmentId);
//        Shipment finalizedShipment = shipmentManager.getShipment(bundleShipmentId);
//
//        //then
//        Assertions.assertThat(finalizedShipment)
//                .extracting("ongoing", "locker.empty", "locker.password")
//                .contains(false, true, "");
//    }
//
//    @Test
//    void everyLockerShouldBeEmpty() {
//        //given
//        //when
//        var realAmmount = parcelLocker.countEmptyLockers();
//
//        //then
//        assertEquals(ParcelLocker.LOCKER_AMMOUNT, realAmmount);
//    }
//
//    @Test
//    void saveShipmentSuccessfully() {
//        //given
//        Shipment shipment = new Shipment(UUID.randomUUID(), fixture.fullLockers.get(0), List.of(fixture.bundle));
//
//        //when
//        shipmentRepository.insert(ShipmentMapper.toShipmentByStartDate(shipment));
//
//        //then
//        Shipment shipmentFromDB = ShipmentMapper.toShipment(shipmentRepository.findById(shipment.getId()));
//        assertNotNull(shipmentFromDB);
//    }
//
//    @Test
//    @SneakyThrows
//    void deleteShipmentSuccessfully() {
//        //given
//        UUID shipmentId = parcelLocker.sendPackage(fixture.bundle);
//
//        //when
//        shipmentManager.removeShipment(shipmentId);
//
//        //then
//        assertThrows(NoSuchElementException.class, () -> shipmentManager.getShipment(shipmentId));
//    }

//    @Test
//    @SneakyThrows
//    void cannotSendTheSamePackageTwice() {
//        //given
//        var bundle = fixture.bundle;
//        parcelLocker.sendPackage(bundle);
//        shipmentManager.getBoxById(bundle.getId());
//
//        //when
//        Executable sendPackage = () -> parcelLocker.sendPackage(bundle);
//
//        //then
//        assertThrows(Exception.class, sendPackage);
//    }
}
