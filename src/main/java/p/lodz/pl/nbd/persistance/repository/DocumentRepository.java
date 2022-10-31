package p.lodz.pl.nbd.persistance.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mongodb.client.MongoCollection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DocumentRepository<T, ID> extends AbstractMongoRepository {

    private final Class<T> documentClass;

    private final String collectionName;

    public <S extends T> void save(S entity) {
        MongoCollection<T> collection = getMongoRepository().getCollection(collectionName, documentClass);
        collection.insertOne(entity);
//        try {
//            getEntityManager().getTransaction().begin();
//            getEntityManager().persist(entity);
//            getEntityManager().getTransaction().commit();
//        } catch (PersistenceException e) {
//            throw e.getCause();
//        }
        //todo check if its working
    }

    public Optional<T> findById(ID id) {
//        return Optional.of(
//                getEntityManager()
//                        .find(documentClass, id));
        return null;
        //todo
    }

    public List<T> findAll() {
        MongoCollection<T> collection = getMongoRepository().getCollection(collectionName, documentClass);
        return collection.find().into(new ArrayList<>());
//        return (List<T>) getEntityManager().createQuery(
//                        "select e from " + documentClass.getSimpleName() + " e")
//                .getResultList();
        //todo
    }
}