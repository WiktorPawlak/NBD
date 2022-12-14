package p.lodz.pl.nbd.persistence.document.box;

import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;

import p.lodz.pl.nbd.persistence.repository.InboxIdentifiers;

public class CreateBoxTypeQueryProvider {

    private final CqlSession session;
    private final EntityHelper<BundleDocument> bundleHelper;
    private final EntityHelper<EnvelopeDocument> envelopeHelper;

    CreateBoxTypeQueryProvider(
            MapperContext mapperContext,
            EntityHelper<BundleDocument> bundleHelper,
            EntityHelper<EnvelopeDocument> envelopeHelper) {
        this.session = mapperContext.getSession();
        this.bundleHelper = bundleHelper;
        this.envelopeHelper = envelopeHelper;
    }

    void create(BoxTypeDocument boxType) {
        if (boxType.getId() == null) {
            boxType.setId(UUID.randomUUID());
        }
        session.execute(switch (boxType.getDiscriminator()) {
            case "bundle" -> {
                BundleDocument bundleDocument = (BundleDocument) boxType;
                yield session.prepare(bundleHelper.insert().build())
                        .bind()
                        .setUuid(InboxIdentifiers.ID, bundleDocument.getId())
                        .setInt(InboxIdentifiers.LENGTH, bundleDocument.getLength())
                        .setInt(InboxIdentifiers.WIDTH, bundleDocument.getWidth())
                        .setInt(InboxIdentifiers.HEIGHT, bundleDocument.getHeight())
                        .setBoolean(InboxIdentifiers.FRAGILE, bundleDocument.getFragile());
            }
            case "envelope" -> {
                EnvelopeDocument envelopeDocument = (EnvelopeDocument) boxType;
                yield session.prepare(envelopeHelper.insert().build())
                        .bind()
                        .setUuid(InboxIdentifiers.ID, envelopeDocument.getId())
                        .setInt(InboxIdentifiers.LENGTH, envelopeDocument.getLength())
                        .setInt(InboxIdentifiers.WIDTH, envelopeDocument.getWidth())
                        .setInt(InboxIdentifiers.HEIGHT, envelopeDocument.getHeight())
                        .setInt(InboxIdentifiers.PRIORITY, envelopeDocument.getPriority());
            }
            default -> throw new IllegalArgumentException();
        });
    }
}
