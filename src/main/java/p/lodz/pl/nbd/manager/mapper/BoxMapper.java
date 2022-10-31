package p.lodz.pl.nbd.manager.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.model.box.BoxType;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistance.document.UniqueIdMgd;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;
import p.lodz.pl.nbd.persistance.document.box.BoxTypeDocument;
import p.lodz.pl.nbd.persistance.document.box.BundleDocument;
import p.lodz.pl.nbd.persistance.document.box.EnvelopeDocument;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class BoxMapper {

    public static List<BoxDocument> toBoxesDocuments(List<Box> boxes) {
        return boxes.stream()
                .map(BoxMapper::toBoxDocument)
                .collect(Collectors.toList());
    }

    private static BoxDocument toBoxDocument(final Box box) {
        return BoxDocument.builder()
                .entityId(new UniqueIdMgd(box.getId()))
                .weight(box.getWeight())
                .boxType(toBoxTypeDocument(box.getBoxType()))
                .build();
    }

    public static List<Box> toBoxes(List<BoxDocument> boxes) {
        return boxes.stream()
                .map(BoxMapper::toBox)
                .collect(Collectors.toList());
    }

    private static Box toBox(final BoxDocument boxDocument) {
        return Box.builder()
                .id(boxDocument.getEntityId().getUuid())
                .boxType(toBoxType(boxDocument.getBoxType()))
                .weight(boxDocument.getWeight())
                .build();
    }

    private static BoxType toBoxType(final BoxTypeDocument boxType) {
        if (boxType instanceof BundleDocument) {
            return toBundle((BundleDocument) boxType);
        } else {
            return toEnvelope((EnvelopeDocument) boxType);
        }
    }

    private static Bundle toBundle(final BundleDocument boxType) {
        return Bundle.builder()
                .fragile(boxType.getFragile())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }

    private static Envelope toEnvelope(final EnvelopeDocument boxType) {
        return Envelope.builder()
                .priority(boxType.getPriority())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }

    private static BoxTypeDocument toBoxTypeDocument(final BoxType boxType) {
        if (boxType instanceof Bundle) {
            return toBundleDocument((Bundle) boxType);
        } else {
            return toEnvelopeDocument((Envelope) boxType);
        }
    }

    private static BundleDocument toBundleDocument(final Bundle boxType) {
        return BundleDocument.builder()
                .fragile(boxType.getFragile())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }

    private static EnvelopeDocument toEnvelopeDocument(final Envelope boxType) {
        return EnvelopeDocument.builder()
                .priority(boxType.getPriority())
                .height(boxType.getHeight())
                .width(boxType.getWidth())
                .length(boxType.getLength())
                .build();
    }
}
