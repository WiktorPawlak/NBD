package p.lodz.pl.nbd.persistance.repository;

import static com.mongodb.client.model.Filters.eq;

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

    public <S extends T> T save(final S entity) {
        MongoCollection<T> collection = getMongoRepository().getCollection(collectionName, documentClass);
        collection.insertOne(entity);
        return entity;
    }

    public Optional<T> findById(final ID id) {
        MongoCollection<T> collection = getMongoRepository().getCollection(collectionName, documentClass);
        return Optional.ofNullable(collection.find(eq("_id", id)).first());
    }

    public List<T> findAll() {
        MongoCollection<T> collection = getMongoRepository().getCollection(collectionName, documentClass);
        return collection.find().into(new ArrayList<>());
    }
}