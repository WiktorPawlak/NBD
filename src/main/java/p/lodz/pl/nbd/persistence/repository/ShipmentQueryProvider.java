package p.lodz.pl.nbd.persistence.repository;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.update;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import lombok.AllArgsConstructor;
import p.lodz.pl.nbd.persistence.config.InboxIdentifiers;
import p.lodz.pl.nbd.persistence.repository.mapper.ShipmentEntityMapper;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;


@AllArgsConstructor
public class ShipmentQueryProvider {

    private CqlSession session;

    public List<ShipmentByStartDate> findAll() {
        SimpleStatement findAllQuery = selectFrom(InboxIdentifiers.NBD_INBOX.toString(), "shipments_by_start_date")
                .all().build();
        return ShipmentEntityMapper.toShipmentsByStartDate(session.execute(findAllQuery).all());
    }

    public List<ShipmentByStartDate> findFinalizedShipments() {
        SimpleStatement findFinalizedQuery = selectFrom(InboxIdentifiers.NBD_INBOX.toString(), "shipments_by_start_date")
                .all()
                .whereColumn("finalizationDate")
                .isNotNull()
                .build();
        return ShipmentEntityMapper.toShipmentsByStartDate(session.execute(findFinalizedQuery).all());
    }

    public void finalizeShipment(final UUID shipmentId) {
        SimpleStatement finalizeQuery = update(InboxIdentifiers.NBD_INBOX.toString(), "shipments_by_start_date")
                .setColumn("finalizationDate", literal(Instant.now()))
                .whereColumn("id").isEqualTo(bindMarker("shipmentId")).build();
        session.execute(finalizeQuery);
    }
}
