package p.lodz.pl.nbd.persistence.repository;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.update;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistence.config.CassandraConfig;
import p.lodz.pl.nbd.persistence.config.InboxIdentifiers;
import p.lodz.pl.nbd.persistence.repository.mapper.ShipmentEntityMapper;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentQueryProvider {

    private static final CqlSession session = CassandraConfig.session;

    public static List<ShipmentByStartDate> findAll() {
        SimpleStatement findAllQuery = selectFrom(InboxIdentifiers.NBD_INBOX.toString(), InboxIdentifiers.SHIPMENTS_BY_START_DATE)
                .all().build();
        return ShipmentEntityMapper.toShipmentsByStartDate(session.execute(findAllQuery).all());
    }

    public static List<ShipmentByStartDate> findFinalizedShipments() {
        SimpleStatement findFinalizedQuery = selectFrom(InboxIdentifiers.NBD_INBOX.toString(), InboxIdentifiers.SHIPMENTS_BY_START_DATE)
                .all()
                //TODO in 4.0 cassandra driver "isNotNull" is not implemented. need to create additional table "ShipmentsByFinalizationDate"
                //to be able to remove allowFiltering()
//                .whereColumn("id").isNotNull()
//                .whereColumn("start_date").isNotNull()
                .whereColumn("finalization_date").isGreaterThan(literal(0))
                .allowFiltering()
                .build();
        return ShipmentEntityMapper.toShipmentsByStartDate(session.execute(findFinalizedQuery).all());
    }

    public static void finalizeShipment(final UUID shipmentId, final Instant startDate) {
        SimpleStatement finalizeQuery = update(InboxIdentifiers.NBD_INBOX.toString(), InboxIdentifiers.SHIPMENTS_BY_START_DATE)
                .setColumn("finalization_date", literal(Instant.now()))
                .whereColumn("id").isEqualTo(literal(shipmentId))
                .whereColumn("start_date").isEqualTo(literal(startDate))
                .build();
        session.execute(finalizeQuery);
    }
}
