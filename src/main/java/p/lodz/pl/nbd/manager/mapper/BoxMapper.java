package p.lodz.pl.nbd.manager.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class BoxMapper {

    public static List<BoxDocument> toBoxesDocuments(List<Box> boxes) {
        return boxes.stream()
                .map(BoxMapper::toBoxDocument)
                .collect(Collectors.toList());
    }

    private static BoxDocument toBoxDocument(final Box box) {
        return BoxDocument.builder()
                //todo: map domain entity to database entity
                .build();
    }
}
