package p.lodz.pl.nbd.persistance.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import p.lodz.pl.nbd.model.Shipment;

import java.util.List;
import java.util.UUID;


public class ShipmentRepository extends EntityRepository<Shipment, UUID> {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

    private final EntityManager em = emf.createEntityManager();

    public ShipmentRepository() {
        super(Shipment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Shipment> getArchivedShipments() {
        TypedQuery<Shipment> shipmentTypedQuery = em.createNamedQuery("Shipment.findArchivedShipments",
                Shipment.class);

        return shipmentTypedQuery.getResultList();
    }

    public void archiveShipment(final UUID id) throws Throwable {
        try {
            em.getTransaction().begin();
            em.find(Shipment.class, id).setOngoing(false);
            em.getTransaction().commit();
        } catch (final PersistenceException e) {
            throw e.getCause();
        }
    }
}