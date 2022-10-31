package p.lodz.pl.nbd.persistance.document.box;

import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistance.document.AbstractDocument;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoxDocument extends AbstractDocument {

    @BsonProperty("weight")
    private double weight;

    @BsonProperty("boxType")
    private BoxTypeDocument boxType;

    @Builder
    @BsonCreator
    public BoxDocument(@BsonProperty("_id") UUID entityId,
                       @BsonProperty("weight") double weight,
                       @BsonProperty("boxType") BoxTypeDocument boxType) {
        super(entityId);
        this.weight = weight;
        this.boxType = boxType;
    }

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
