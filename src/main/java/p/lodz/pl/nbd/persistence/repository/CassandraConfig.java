package p.lodz.pl.nbd.persistence.repository;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import lombok.Getter;


@Getter
public class CassandraConfig {

    private final CqlSession session = CqlSession.builder().build();

    private final SimpleStatement createKeyspace = createKeyspace(InboxIdentifiers.NBD_INBOX)
            .ifNotExists()
            .withSimpleStrategy(2)
            .withDurableWrites(true)
            .build();

    private final SimpleStatement createBoxTable = SchemaBuilder.createTable("box")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("weight", DataTypes.DOUBLE)
            .withColumn("box_type_id", DataTypes.UUID)
            .withColumn("box_type_discriminator", DataTypes.TEXT)
            .withColumn("box_type_length", DataTypes.INT)
            .withColumn("box_type_width", DataTypes.INT)
            .withColumn("box_type_height", DataTypes.INT)
            .build();

    private final SimpleStatement createBundleTable = SchemaBuilder.createTable("bundle")
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("fragile", DataTypes.BOOLEAN)
            .build();

    private final SimpleStatement createEnvelopeTable = SchemaBuilder.createTable("envelope")
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("priority", DataTypes.INT)
            .build();

    private final SimpleStatement createShipmentTable = SchemaBuilder.createTable("box")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("boxesCost", DataTypes.DOUBLE)
            .withColumn("boxes", DataTypes.frozenListOf(DataTypes.custom("box")))
            .withColumn("ongoing", DataTypes.BOOLEAN)
            .withColumn("locker_empty", DataTypes.BOOLEAN)
            .withColumn("locker_password", DataTypes.TEXT)
            .withColumn("box_type_height", DataTypes.INT)
            .build();
}
