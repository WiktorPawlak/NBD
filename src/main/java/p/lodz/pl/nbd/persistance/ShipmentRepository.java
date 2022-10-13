package p.lodz.pl.nbd.persistance;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;
import p.lodz.pl.nbd.model.Shipment;

import java.util.List;
import java.util.UUID;

@Stateless
public class ShipmentRepository extends EntityRepository<Shipment, UUID> {

    @PersistenceContext(unitName = "inmemory")
    private EntityManager em;

    public ShipmentRepository() {
        super(Shipment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Shipment> getArchivedShipments() {
        TypedQuery<Shipment> shipmentTypedQuery = em.createNamedQuery("Shipments.findArchivedShipments",
                Shipment.class);

        return shipmentTypedQuery.getResultList();
    }

    public void archiveShipment(UUID id) {
        try {
            getEntityManager()
                    .find(Shipment.class, id)
                    .setOngoing(false);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            throw (ConstraintViolationException) e.getCause();
        }
    }
}