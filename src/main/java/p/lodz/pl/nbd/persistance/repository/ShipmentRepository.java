package p.lodz.pl.nbd.persistance.repository;


import java.util.List;
import java.util.UUID;

import p.lodz.pl.nbd.persistance.document.shipment.ShipmentDocument;


public class ShipmentRepository extends DocumentRepository<ShipmentDocument, UUID> {
    //todo write new CRUD methods
    public static final String COLLECTION_NAME = "Shipments";

    public ShipmentRepository() {
        super(ShipmentDocument.class, COLLECTION_NAME);
    }

    public List<ShipmentDocument> getArchivedShipments() {
//        TypedQuery<ShipmentDocument> shipmentTypedQuery = em.createNamedQuery("Shipment.findArchivedShipments",
//                ShipmentDocument.class);
//
//        return shipmentTypedQuery.getResultList();
        return null;
        //todo
    }

    public void archiveShipment(UUID id) throws Throwable {
//        try {
//            em.getTransaction().begin();
//            em.find(ShipmentDocument.class, id).setOngoing(false);
//            em.getTransaction().commit();
//        } catch (PersistenceException e) {
//            throw e.getCause();
//        }
        //todo
    }
}