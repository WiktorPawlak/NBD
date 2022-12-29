package p.lodz.pl.nbd.manager.mapper;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class BoxMapper {

    public static List<Bundle> toBundles(List<BundleUdt> bundlesTypes) {
        return bundlesTypes.stream()
                .map(BoxMapper::toBundle)
                .toList();
    }

    public static Bundle toBundle(final BundleUdt boxType) {
        return Bundle.lombokBuilder()
                .fragile(boxType.getFragile())
                .weight(boxType.getWeight())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .lombokBuild();
    }

    public static List<Envelope> toEnvelopes(List<EnvelopeUdt> envelopesTypes) {
        return envelopesTypes.stream()
                .map(BoxMapper::toEnvelope)
                .toList();
    }

    public static Envelope toEnvelope(final EnvelopeUdt boxType) {
        return Envelope.lombokBuilder()
                .priority(boxType.getPriority())
                .weight(boxType.getWeight())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .lombokBuild();
    }

    public static List<BundleUdt> toBundlesTypes(List<Bundle> bundles) {
        return bundles.stream()
                .map(BoxMapper::toBundleType)
                .toList();
    }

    public static List<EnvelopeUdt> toEnvelopesTypes(List<Envelope> envelopes) {
        return envelopes.stream()
                .map(BoxMapper::toEnvelopeType)
                .toList();
    }

    public static BundleUdt toBundleType(final Bundle boxType) {
        return BundleUdt.builder()
                .fragile(boxType.getFragile())
                .weight(boxType.getWeight())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }

    public static EnvelopeUdt toEnvelopeType(final Envelope boxType) {
        return EnvelopeUdt.builder()
                .priority(boxType.getPriority())
                .weight(boxType.getWeight())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }
}
