package p.lodz.pl.nbd.persistence.document.shipment;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import p.lodz.pl.nbd.persistence.document.box.BoxDocument;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentDocument {

    private UUID id;

    private LockerDocument locker;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoxDocument> boxes;

    private double boxesCost;

    @Setter
    private boolean ongoing;

    @Builder
    public ShipmentDocument(UUID id,
                            LockerDocument locker,
                            List<BoxDocument> boxes) {
        this.id = id;
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(BoxDocument::getBoxCost).sum();
    }
}
