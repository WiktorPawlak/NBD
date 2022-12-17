package p.lodz.pl.nbd.persistence.repository;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistence.document.InboxMapper;
import p.lodz.pl.nbd.persistence.document.InboxMapperBuilder;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CassandraConfig {

    public static final CqlSession session = CqlSession.builder()
            .build();

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
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("fragile", DataTypes.BOOLEAN)
            .build();

    private static final SimpleStatement createEnvelopeTable = SchemaBuilder.createTable("envelope")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("priority", DataTypes.INT)
            .build();

    private static final SimpleStatement createBoxTable = SchemaBuilder.createTable("box")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("weight", DataTypes.DOUBLE)
            .withColumn("box_type_id", DataTypes.UUID)
            .build();

    private static final SimpleStatement createLockerType = SchemaBuilder.createType(InboxIdentifiers.NBD_INBOX, CqlIdentifier.fromCql("locker"))
            .ifNotExists()
            .withField("locker_empty", DataTypes.BOOLEAN)
            .withField("locker_password", DataTypes.TEXT).build();

    private static final SimpleStatement createShipmentTable = SchemaBuilder.createTable("shipment")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("boxes_cost", DataTypes.DOUBLE)
            .withColumn("boxes", DataTypes.listOf(DataTypes.UUID))
            .withColumn("ongoing", DataTypes.BOOLEAN)
            .withColumn("locker", DataTypes.custom("locker"))
            .build();

    static {
        session.execute(createKeyspace);
        session.execute(createBundleTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createEnvelopeTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createBoxTypeTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createBoxTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createLockerType.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createShipmentTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
    }
}
