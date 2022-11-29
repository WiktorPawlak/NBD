package p.lodz.pl.nbd.test.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import p.lodz.pl.nbd.BoxesLockersFixture;
import p.lodz.pl.nbd.manager.CacheableShipmentManager;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.persistance.cache.JedisCache;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;
import p.lodz.pl.nbd.persistance.repository.ShipmentRepository;

class CacheableShipmentManagerTest {

    private BoxesLockersFixture fixture;

    private CacheableShipmentManager shipmentManager;

    @BeforeEach
    void init() {
        shipmentManager = new CacheableShipmentManager(ShipmentManager.of(new ShipmentRepository()), new JedisCache());
        fixture = new BoxesLockersFixture();
    }

    @Test
    void shouldGetShipmentDespiteDisconnectedRedis() {
        shipmentManager.getJedisCache().jedis.close();

        ShipmentDocument shipmentDocument = shipmentManager.addShipment(fixture.fullLockers.get(0), List.of(fixture.bundle));
        Shipment shipment = shipmentManager.getShipment(shipmentDocument.getId());

        assertThat(shipmentDocument).usingRecursiveComparison().isEqualTo(shipment);
        assertTrue(shipmentManager.getJedisCache().jedis.getPool().isClosed());
    }

    @Test
    @SneakyThrows
    void saveShipmentToCacheSuccessfully() {
        var shipmentDocument = fixture.shipmentDocument;
        shipmentManager.cacheShipment(shipmentDocument);

        ShipmentDocument cachedShipment = shipmentManager.getCachedShipmentDocument(shipmentDocument.getId());

        assertThat(shipmentDocument).usingRecursiveComparison().isEqualTo(cachedShipment);
    }

    @Test
    void cacheShipmentUponFirstGetSuccessfully() {
        ShipmentDocument shipmentDocument = shipmentManager.addShipment(fixture.fullLockers.get(0), List.of(fixture.bundle));

        Shipment shipment = shipmentManager.getShipment(shipmentDocument.getId());

        ShipmentDocument cachedShipment = shipmentManager.getCachedShipmentDocument(shipmentDocument.getId());

        assertThat(shipment).usingRecursiveComparison().isEqualTo(cachedShipment);
    }

    @Test
    void shipmentIsUpdatedInCacheUponFinalizationSuccessfully() {
        var shipmentDocument = shipmentManager.addShipment(fixture.fullLockers.get(0), List.of(fixture.envelope));

        shipmentManager.finalizeShipment(shipmentDocument.getId());
        var finalizedCachedShipment = shipmentManager.getCachedShipmentDocument(shipmentDocument.getId());

        Assertions.assertThat(finalizedCachedShipment)
                .extracting("ongoing", "locker.empty", "locker.password")
                .contains(false, true, "");
    }

    @Test
    void cacheIsInvalidatedUponRemovingShipment() {
        var shipmentDocument = shipmentManager.addShipment(fixture.fullLockers.get(0), List.of(fixture.envelope));
        var id = shipmentDocument.getId();

        shipmentManager.removeShipment(id);

        assertThrows(NoSuchElementException.class, () -> shipmentManager.getShipment(id));
        assertNull(shipmentManager.getCachedShipmentDocument(id));
    }
}
