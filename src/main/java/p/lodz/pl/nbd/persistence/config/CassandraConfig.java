package p.lodz.pl.nbd.persistence.config;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
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

    private static final SimpleStatement createBundleType = SchemaBuilder.createType("bundle_type")
            .ifNotExists()
            .withField("fragile", DataTypes.BOOLEAN)
            .withField("weight", DataTypes.DOUBLE)
            .withField("length", DataTypes.INT)
            .withField("width", DataTypes.INT)
            .withField("height", DataTypes.INT)
            .build();

    private static final SimpleStatement createEnvelopeType = SchemaBuilder.createType("envelope_type")
            .ifNotExists()
            .withField("priority", DataTypes.INT)
            .withField("weight", DataTypes.DOUBLE)
            .withField("length", DataTypes.INT)
            .withField("width", DataTypes.INT)
            .withField("height", DataTypes.INT)
            .build();

    private static final SimpleStatement createShipmentTable = SchemaBuilder.createTable("shipments_by_start_date")
            .ifNotExists()
            .withPartitionKey("id", DataTypes.UUID)
            .withColumn("boxes_cost", DataTypes.DOUBLE)
            .withColumn("start_date", DataTypes.TIMESTAMP)
            .withColumn("finalization_date", DataTypes.TIMESTAMP)
            .withColumn("boxes", DataTypes.frozenListOf(SchemaBuilder.udt("bundle_type", true)))
            .withColumn("envelopes", DataTypes.frozenListOf(SchemaBuilder.udt("envelope_type", true)))
            .build();

    private static final CodecRegistry codecRegistry = session.getContext().getCodecRegistry();

    private static final UserDefinedType bundleTypeUdt = session.getMetadata()
            .getKeyspace(InboxIdentifiers.NBD_INBOX)
            .flatMap(ks -> ks.getUserDefinedType("bundle_type"))
            .orElseThrow(IllegalStateException::new);
    private static final TypeCodec<UdtValue> innerBundleCodec = codecRegistry.codecFor(bundleTypeUdt);
    private static final BundleUdtCodec BUNDLE_UDT_CODEC = new BundleUdtCodec(innerBundleCodec,
            GenericType.of(BundleUdt.class));
    private static final UserDefinedType envelopeTypeUdt = session.getMetadata()
            .getKeyspace(InboxIdentifiers.NBD_INBOX)
            .flatMap(ks -> ks.getUserDefinedType("envelope_type"))
            .orElseThrow(IllegalStateException::new);
    private static final TypeCodec<UdtValue> innerEnvelopeCodec = codecRegistry.codecFor(envelopeTypeUdt);
    private static final EnvelopeUdtCodec ENVELOPE_UDT_CODEC = new EnvelopeUdtCodec(innerEnvelopeCodec,
            GenericType.of(EnvelopeUdt.class));

    static {
        session.execute(createKeyspace);
        session.execute(createBundleType.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createEnvelopeType.setKeyspace(InboxIdentifiers.NBD_INBOX));
        session.execute(createShipmentTable.setKeyspace(InboxIdentifiers.NBD_INBOX));
        ((MutableCodecRegistry) codecRegistry).register(BUNDLE_UDT_CODEC);
        ((MutableCodecRegistry) codecRegistry).register(ENVELOPE_UDT_CODEC);
    }
}
