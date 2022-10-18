package p.lodz.pl.nbd.persistance.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class EntityRepository<T, ID> {

    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public <S extends T> void save(final S entity) throws Throwable {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        } catch (final PersistenceException e) {
            throw e.getCause();
        }
    }

    public Optional<T> findById(final ID id) {
        return Optional.of(
                getEntityManager()
                        .find(entityClass, id));
    }

    public List<T> findAll() {
        return (List<T>) getEntityManager().createQuery(
                        "select e from " + entityClass.getSimpleName() + " e")
                .getResultList();
    }
}