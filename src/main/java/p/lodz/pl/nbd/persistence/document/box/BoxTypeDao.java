package p.lodz.pl.nbd.persistence.document.box;


import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;


@Dao
public interface BoxTypeDao {

    @Select
    BoxTypeDocument getBoxType(UUID id);

    @QueryProvider(
            providerClass = CreateBoxTypeQueryProvider.class,
            entityHelpers = {
                    BundleDocument.class,
                    EnvelopeDocument.class
            })
    void create(BoxTypeDocument bundle);

    @Update(nullSavingStrategy = NullSavingStrategy.SET_TO_NULL)
    void update(BoxTypeDocument boxTypeTemplate);

    @Delete
    void delete(BoxTypeDocument boxTypeDocument);
}
