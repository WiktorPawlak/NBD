package p.lodz.pl.nbd.persistance.repository;

import java.util.List;

import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;
import p.lodz.pl.nbd.persistance.document.box.BoxTypeDocument;
import p.lodz.pl.nbd.persistance.document.box.BundleDocument;
import p.lodz.pl.nbd.persistance.document.box.EnvelopeDocument;

public abstract class AbstractMongoRepository implements AutoCloseable {

    private final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private final MongoCredential credential = MongoCredential
            .createCredential("admin", "admin", "adminpassword".toCharArray());

    private final CodecRegistry pojoCodecRegistry =
            CodecRegistries.fromProviders(PojoCodecProvider.builder()
                    .automatic(true)
                    .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                    .build());

    @Getter
    private MongoClient mongoClient;

    @Getter
    private MongoDatabase mongoRepository;

    public void initDbConnection() {
        ClassModel<BoxDocument> boxDocument = ClassModel.builder(BoxDocument.class).enableDiscriminator(true).build();
        ClassModel<BoxTypeDocument> boxTypeDocument = ClassModel.builder(BoxTypeDocument.class).enableDiscriminator(true).build();
        ClassModel<EnvelopeDocument> envelopeDocument = ClassModel.builder(EnvelopeDocument.class).enableDiscriminator(true).build();
        ClassModel<BundleDocument> bundleDocument = ClassModel.builder(BundleDocument.class).enableDiscriminator(true).build();
        PojoCodecProvider boxDocumentProvider = PojoCodecProvider.builder()
                .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                .register(boxDocument, boxTypeDocument, envelopeDocument, bundleDocument).build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(boxDocumentProvider),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry))
                .build();

        mongoClient = MongoClients.create(settings);
        mongoRepository = mongoClient.getDatabase("NBD-Z2-DB");
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
