package p.lodz.pl.nbd.persistence.repository;


import java.util.Optional;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;

import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;


@Dao
public interface ShipmentDao {

    @Select
    Optional<ShipmentByStartDate> findById(final UUID id);

    @Insert
    ShipmentByStartDate insert(final ShipmentByStartDate shipmentByStartDate);

    @Delete(entityClass = ShipmentByStartDate.class)
    void delete(final UUID id);
}
