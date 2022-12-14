package p.lodz.pl.nbd.persistence.document.box;


import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;


@Dao
public interface BoxDao {

    @Select
    BoxDocument getBox(UUID id);

    @QueryProvider(
            providerClass = CreateBoxQueryProvider.class,
            entityHelpers = {
                    BoxDocument.class
            })
    void create(BoxDocument bundle);

    @Update(nullSavingStrategy = NullSavingStrategy.SET_TO_NULL)
    void update(BoxDocument boxTemplate);

    @Delete(entityClass = BoxDocument.class)
    void delete(BoxDocument boxDocument);
}
