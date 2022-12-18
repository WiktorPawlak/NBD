package p.lodz.pl.nbd.persistence.table.mapper;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

import p.lodz.pl.nbd.persistence.repository.ShipmentDao;


@Mapper
public interface ShipmentMapper {

    @DaoFactory
    ShipmentDao shipmentDao(@DaoKeyspace CqlIdentifier keyspace);
}
