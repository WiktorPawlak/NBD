package p.lodz.pl.nbd.persistence.config;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistence.table.mapper.ShipmentMapper;
import p.lodz.pl.nbd.persistence.table.mapper.ShipmentMapperBuilder;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;
import p.lodz.pl.nbd.persistence.udt.codec.BundleUdtCodec;
import p.lodz.pl.nbd.persistence.udt.codec.EnvelopeUdtCodec;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CassandraConfig {

    public static final CqlSession session = CqlSession.builder()
            .build();

    public static final ShipmentMapper SHIPMENT_MAPPER = new ShipmentMapperBuilder(session).build();

    private static final SimpleStatement createKeyspace = createKeyspace(InboxIdentifiers.NBD_INBOX)
            .ifNotExists()
            .withSimpleStrategy(2)
            .withDurableWrites(true)
            .build();

    private static final SimpleStatement createBundleType = SchemaBuilder.createType(InboxIdentifiers.BUNDLE_TYPE)
            .ifNotExists()
            .withField(InboxIdentifiers.FRAGILE, DataTypes.BOOLEAN)
            .withField(InboxIdentifiers.WEIGHT, DataTypes.DOUBLE)
            .withField(InboxIdentifiers.LENGTH, DataTypes.INT)
            .withField(InboxIdentifiers.WIDTH, DataTypes.INT)
            .withField(InboxIdentifiers.HEIGHT, DataTypes.INT)
            .build();

    private static final SimpleStatement createEnvelopeType = SchemaBuilder.createType(InboxIdentifiers.ENVELOPE_TYPE)
            .ifNotExists()
            .withField(InboxIdentifiers.PRIORITY, DataTypes.INT)
            .withField(InboxIdentifiers.WEIGHT, DataTypes.DOUBLE)
            .withField(InboxIdentifiers.LENGTH, DataTypes.INT)
            .withField(InboxIdentifiers.WIDTH, DataTypes.INT)
            .withField(InboxIdentifiers.HEIGHT, DataTypes.INT)
            .build();

    private static final SimpleStatement createShipmentTable = SchemaBuilder.createTable(InboxIdentifiers.SHIPMENTS_BY_START_DATE)
            .ifNotExists()
            .withPartitionKey(InboxIdentifiers.ID, DataTypes.UUID)
            .withColumn(InboxIdentifiers.BOXES_COST, DataTypes.DOUBLE)
            .withClusteringColumn(InboxIdentifiers.START_DATE, DataTypes.TIMESTAMP)
            .withColumn(InboxIdentifiers.FINALIZATION_DATE, DataTypes.TIMESTAMP)
            .withColumn(InboxIdentifiers.BUNDLES, DataTypes.frozenListOf(SchemaBuilder.udt(InboxIdentifiers.BUNDLE_TYPE, true)))
            .withColumn(InboxIdentifiers.ENVELOPES, DataTypes.frozenListOf(SchemaBuilder.udt(InboxIdentifiers.ENVELOPE_TYPE, true)))
            .withClusteringOrder(InboxIdentifiers.START_DATE, ClusteringOrder.DESC)
            .build();

    public static void initDb() {
        session.execute(createKeyspace);
        session.execute(createBundleType.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createEnvelopeType.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createShipmentTable.setKeyspace(InboxIdentifiers.NBD_INBOX));

        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();

        BundleUdtCodec bundleUdtCodec = getBundleUdtCodec(codecRegistry);

        EnvelopeUdtCodec envelopeUdtCodec = getEnvelopeUdtCodec(codecRegistry);

        ((MutableCodecRegistry) codecRegistry).register(bundleUdtCodec);
        ((MutableCodecRegistry) codecRegistry).register(envelopeUdtCodec);
    }

    public static void clearShipmentsByStartDate() {
        session.execute(QueryBuilder.truncate(InboxIdentifiers.NBD_INBOX.toString(),
                InboxIdentifiers.SHIPMENTS_BY_START_DATE).build());
    }

    private static BundleUdtCodec getBundleUdtCodec(final CodecRegistry codecRegistry) {
        UserDefinedType bundleTypeUdt = session.getMetadata()
                .getKeyspace(InboxIdentifiers.NBD_INBOX)
                .flatMap(ks -> ks.getUserDefinedType(InboxIdentifiers.BUNDLE_TYPE))
                .orElseThrow(() -> new IllegalStateException(InboxIdentifiers.UDT_DEFINITION_NOT_FOUND));
        TypeCodec<UdtValue> innerBundleCodec = codecRegistry.codecFor(bundleTypeUdt);
        return new BundleUdtCodec(innerBundleCodec,
                GenericType.of(BundleUdt.class));
    }

    private static EnvelopeUdtCodec getEnvelopeUdtCodec(final CodecRegistry codecRegistry) {
        UserDefinedType envelopeTypeUdt = session.getMetadata()
                .getKeyspace(InboxIdentifiers.NBD_INBOX)
                .flatMap(ks -> ks.getUserDefinedType(InboxIdentifiers.ENVELOPE_TYPE))
                .orElseThrow(() -> new IllegalStateException(InboxIdentifiers.UDT_DEFINITION_NOT_FOUND));
        TypeCodec<UdtValue> innerEnvelopeCodec = codecRegistry.codecFor(envelopeTypeUdt);
        return new EnvelopeUdtCodec(innerEnvelopeCodec,
                GenericType.of(EnvelopeUdt.class));
    }
}
