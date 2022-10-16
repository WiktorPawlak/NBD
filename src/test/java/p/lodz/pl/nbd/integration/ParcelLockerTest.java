package p.lodz.pl.nbd.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.pl.nbd.persistance.ShipmentRepository;


class ParcelLockerTest {
    private ShipmentRepository shipmentRepository;

    @BeforeEach
    void init() {
        shipmentRepository = new ShipmentRepository();
    }

    @Test
    void shouldLive() {
        shipmentRepository.getArchivedShipments();
    }
}
