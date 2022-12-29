package p.lodz.pl.nbd.manager;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentByStartDate;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipments;
import static p.lodz.pl.nbd.persistence.repository.ShipmentQueryProvider.findAll;
import static p.lodz.pl.nbd.persistence.repository.ShipmentQueryProvider.findFinalizedShipments;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.Shipment;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistence.config.CassandraConfig;
import p.lodz.pl.nbd.persistence.config.InboxIdentifiers;
import p.lodz.pl.nbd.persistence.repository.ShipmentDao;
import p.lodz.pl.nbd.persistence.repository.ShipmentQueryProvider;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ShipmentManager {

    private final ShipmentDao shipmentsRepository = CassandraConfig.SHIPMENT_MAPPER.shipmentDao(InboxIdentifiers.NBD_INBOX);

    public List<Shipment> getAllShipments() {
        return toShipments(findAll());
    }

    public Shipment getShipment(final UUID shipmentId) {
        return toShipment(shipmentsRepository.findById(shipmentId).orElseThrow());
    }

    public List<Shipment> getFinalizedShipments() {
        return toShipments(findFinalizedShipments());
    }

    public UUID addShipment(final List<Bundle> bundles, List<Envelope> envelopes) {
        Shipment shipment = new Shipment(UUID.randomUUID(), bundles, envelopes);
        shipmentsRepository.insert(toShipmentByStartDate(shipment));
        return shipment.getId();
    }

    public void finalizeShipment(final UUID shipmentId, final Instant startDate) {
        ShipmentQueryProvider.finalizeShipment(shipmentId, startDate);
    }

    public void removeShipment(final UUID shipmentId) {
        shipmentsRepository.delete(shipmentId);
    }
}
