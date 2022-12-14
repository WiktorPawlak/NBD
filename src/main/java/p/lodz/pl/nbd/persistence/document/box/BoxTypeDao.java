package p.lodz.pl.nbd.persistence.document.box;


import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;


@Dao
public interface BoxTypeDao {
    @QueryProvider(
            providerClass = CreateBoxTypeQueryProvider.class,
            entityHelpers = {
                    BundleDocument.class,
                    EnvelopeDocument.class
            })
    void create(BoxTypeDocument bundle);
}
