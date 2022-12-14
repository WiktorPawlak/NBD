package p.lodz.pl.nbd.persistence.document.shipment;


import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;


@Dao
public interface ShipmentDao {

    @Select
    ShipmentDocument findById(UUID id);

    @QueryProvider(
            providerClass = CreateShipmentQueryProvider.class,
            entityHelpers = {
                    ShipmentDocument.class
            })
    ShipmentDocument create(ShipmentDocument shipmentDocument);

    @Update(nullSavingStrategy = NullSavingStrategy.SET_TO_NULL)
    void update(ShipmentDocument shipmentTemplate);

    @Delete(entityClass = ShipmentDocument.class)
    void delete(UUID id);
}
