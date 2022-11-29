package p.lodz.pl.nbd.persistance.document.shipment;

import java.util.List;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentDocument extends AbstractDocument {

    @BsonProperty("locker")
    @JsonbProperty("locker")
    private LockerDocument locker;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonbProperty("boxes")
    private List<BoxDocument> boxes;

    @BsonProperty("boxCost")
    @JsonbProperty("boxCost")
    private double boxesCost;

    @Setter
    @BsonProperty("ongoing")
    @JsonbProperty("ongoing")
    private boolean ongoing;

    @BsonCreator
    @Builder
    @JsonbCreator
    public ShipmentDocument(@JsonbProperty("id") @BsonProperty("id") UUID id,
                            @JsonbProperty("locker") @BsonProperty("locker") LockerDocument locker,
                            @JsonbProperty("boxes") @BsonProperty("boxes") List<BoxDocument> boxes) {
        super(id);
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(BoxDocument::getBoxCost).sum();
    }
}
