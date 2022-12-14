package p.lodz.pl.nbd.persistence.document;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

import p.lodz.pl.nbd.persistence.document.box.BoxDao;
import p.lodz.pl.nbd.persistence.document.box.BoxTypeDao;
import p.lodz.pl.nbd.persistence.document.shipment.ShipmentDao;


@Mapper
public interface InboxMapper {

    @DaoFactory
    ShipmentDao shipmentDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BoxDao boxDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BoxTypeDao boxtypeDao(@DaoKeyspace CqlIdentifier keyspace);

}
