package p.lodz.pl.nbd.persistance.document.shipment;

import java.util.List;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import p.lodz.pl.nbd.persistance.document.AbstractDocument;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;


@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipmentDocument extends AbstractDocument {

    @BsonProperty("locker")
    private LockerDocument locker;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoxDocument> boxes;

    @BsonProperty("boxCost")
    private double boxesCost;

    @Setter
    @BsonProperty("ongoing")
    private boolean ongoing;

    @Builder
    public ShipmentDocument(@BsonProperty("_id") UUID id,
                            @BsonProperty("locker") LockerDocument locker,
                            @BsonProperty("boxes") List<BoxDocument> boxes) {
        super(id);
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(BoxDocument::getBoxCost).sum();
    }
}
