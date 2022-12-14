package p.lodz.pl.nbd.persistence.document.shipment;

import java.util.Objects;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;

import p.lodz.pl.nbd.persistence.repository.InboxIdentifiers;

public class CreateShipmentQueryProvider {

    private final CqlSession session;

    private final PreparedStatement createShipment;

    CreateShipmentQueryProvider(
            MapperContext mapperContext,
            EntityHelper<ShipmentDocument> shipmentDocumentEntityHelper) {
        this.session = mapperContext.getSession();

        createShipment = session.prepare(shipmentDocumentEntityHelper.insert().asCql());
    }

    ShipmentDocument create(ShipmentDocument shipmentDocument) {
        if (shipmentDocument.getId() == null) {
            shipmentDocument.setId(UUID.randomUUID());
        }

        return Objects.requireNonNull(session.execute(
                        createShipment.bind(shipmentDocument)).one()
                .get(InboxIdentifiers.ID, ShipmentDocument.class));
    }
}
