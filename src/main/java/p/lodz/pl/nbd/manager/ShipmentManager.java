package p.lodz.pl.nbd.manager;

import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipment;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipmentByStartDate;
import static p.lodz.pl.nbd.manager.mapper.ShipmentMapper.toShipments;

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

    private final ShipmentQueryProvider shipmentQueryProvider = new ShipmentQueryProvider(CassandraConfig.session);

    public List<Shipment> getAllShipments() {
        return toShipments(shipmentQueryProvider.findAll());
    }

    public Shipment getShipment(final UUID shipmentId) {
        return toShipment(shipmentsRepository.findById(shipmentId));
    }

    public List<Shipment> getFinalizedShipments() {
        return toShipments(shipmentQueryProvider.findFinalizedShipments());
    }

    public UUID addShipment(final List<Bundle> bundles, List<Envelope> envelopes) {
        Shipment shipment = new Shipment(UUID.randomUUID(), bundles, envelopes);
        return shipmentsRepository.insert(toShipmentByStartDate(shipment)).getId();
    }

    public void finalizeShipment(final UUID shipmentId) {
        shipmentQueryProvider.finalizeShipment(shipmentId);
    }

    public void removeShipment(final UUID shipmentId) {
        shipmentsRepository.delete(shipmentId);
    }
}
