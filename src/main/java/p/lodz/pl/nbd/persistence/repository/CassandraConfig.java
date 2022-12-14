package p.lodz.pl.nbd.persistence.repository;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import p.lodz.pl.nbd.persistence.document.InboxMapper;
import p.lodz.pl.nbd.persistence.document.InboxMapperBuilder;


public final class CassandraConfig {

    public static final CqlSession session = CqlSession.builder().build();

    public static final InboxMapper inboxMapper = new InboxMapperBuilder(session).build();

    private static final SimpleStatement createKeyspace = createKeyspace(InboxIdentifiers.NBD_INBOX)
            .ifNotExists()
            .withSimpleStrategy(2)
            .withDurableWrites(true)
            .build();

    private static final SimpleStatement createBoxTypeTable = SchemaBuilder.createTable("box_type")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("discriminator", DataTypes.TEXT)
            .withColumn("length", DataTypes.INT)
            .withColumn("width", DataTypes.INT)
            .withColumn("height", DataTypes.INT)
            .build();

    private static final SimpleStatement createBundleTable = SchemaBuilder.createTable("bundle")
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("fragile", DataTypes.BOOLEAN)
            .build();

    private static final SimpleStatement createEnvelopeTable = SchemaBuilder.createTable("envelope")
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("priority", DataTypes.INT)
            .build();

    private static final SimpleStatement createBoxTable = SchemaBuilder.createTable("box")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("weight", DataTypes.DOUBLE)
            .withColumn("box_type", DataTypes.custom("box_type"))
            .build();

    private static final SimpleStatement createShipmentTable = SchemaBuilder.createTable("box")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("boxesCost", DataTypes.DOUBLE)
            .withColumn("boxes", DataTypes.frozenListOf(DataTypes.custom("box")))
            .withColumn("ongoing", DataTypes.BOOLEAN)
            .withColumn("locker_empty", DataTypes.BOOLEAN)
            .withColumn("locker_password", DataTypes.TEXT)
            .build();

    {
        session.execute(createKeyspace);
        session.execute(createBundleTable);
        session.execute(createEnvelopeTable);
        session.execute(createBoxTypeTable);
        session.execute(createBoxTable);
        session.execute(createShipmentTable);
    }
}
