package p.lodz.pl.nbd.manager;

import java.util.List;
import java.util.UUID;

import jakarta.json.bind.Jsonb;
import lombok.Getter;
import p.lodz.pl.nbd.manager.mapper.ShipmentMapper;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.ShipmentService;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.cache.JedisCache;
import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;
import redis.clients.jedis.JedisPooled;


public class CacheableShipmentManager implements ShipmentService {

    public static final int TTL = 10800;
    private final String shipmentPrefix = "shipmentDocument:";
    private final ShipmentManager shipmentManager;
    @Getter
    private final JedisCache jedisCache;
    private final JedisPooled jedis;
    private final Jsonb jsonb;

    public CacheableShipmentManager(final ShipmentManager shipmentManager, final JedisCache jedisCache) {
        this.shipmentManager = shipmentManager;
        this.jedisCache = jedisCache;
        jedis = jedisCache.jedis;
        jsonb = jedisCache.jsonb;
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentManager.getAllShipments();
    }

    @Override
    public Shipment getShipment(final UUID shipmentId) {
        ShipmentDocument shipmentDocument = getCachedShipmentDocument(shipmentId);

        if (shipmentDocument != null) {
            return ShipmentMapper.toShipment(shipmentDocument);
        } else {
            var shipment = shipmentManager.getShipment(shipmentId);
            cacheShipment(ShipmentMapper.toShipmentDocument(shipment));
            return shipment;
        }
    }

    @Override
    public List<Shipment> getArchivedShipments() {
        return shipmentManager.getArchivedShipments();
    }

    @Override
    public ShipmentDocument addShipment(final Locker locker, final List<Box> boxes) {
        return shipmentManager.addShipment(locker, boxes);
    }

    @Override
    public Box getBoxById(final UUID boxId) {
        return shipmentManager.getBoxById(boxId);
    }

    @Override
    public boolean checkIfBoxWasSent(final UUID boxId) {
        return shipmentManager.checkIfBoxWasSent(boxId);
    }

    @Override
    public void finalizeShipment(final UUID shipmentId) { //update cache
        shipmentManager.finalizeShipment(shipmentId);
        var shipment = shipmentManager.getShipment(shipmentId);
        cacheShipment(ShipmentMapper.toShipmentDocument(shipment));
    }

    @Override
    public void removeShipment(final UUID shipmentId) {
        shipmentManager.removeShipment(shipmentId);
        removeShipmentFromCache(shipmentId);
    }

    public ShipmentDocument getCachedShipmentDocument(final UUID shipmentId) {
        if (checkRedisAvailability()) return null;
        String shipment = jedis.get(shipmentPrefix + shipmentId.toString());
        ShipmentDocument cachedShipment = null;

        if (shipment != null) {
            cachedShipment = jsonb.fromJson(shipment, ShipmentDocument.class);
        }

        return cachedShipment;
    }

    public void cacheShipment(final ShipmentDocument shipmentDocument) {
        if (checkRedisAvailability()) return;
        String shipmentJson = jsonb.toJson(shipmentDocument, ShipmentDocument.class);

        jedis.set(shipmentPrefix + shipmentDocument.getId().toString(), shipmentJson);
        jedis.expire(shipmentPrefix + shipmentDocument.getId(), TTL);
    }

    public void removeShipmentFromCache(final UUID shipmentId) {
        if (checkRedisAvailability()) return;
        jedis.del(shipmentPrefix + shipmentId.toString());
    }

    private boolean checkRedisAvailability() {
        return jedis.getPool().isClosed();
    }
}
