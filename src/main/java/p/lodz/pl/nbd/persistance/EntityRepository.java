package p.lodz.pl.nbd.persistance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class EntityRepository<T, ID> {

    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public <S extends T> void save(S entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        } catch (PersistenceException e) {
            throw (ConstraintViolationException) e.getCause();
        }
    }

    public Optional<T> findById(ID id) {
        return Optional.of(getEntityManager().find(entityClass, id));
    }

    public List<T> findAll() {
        return (List<T>) getEntityManager().createQuery(
                        "select e from " + entityClass.getSimpleName() + " e")
                .getResultList();
    }
}