package p.lodz.pl.nbd.persistance.document.box;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistance.document.AbstractDocument;
import p.lodz.pl.nbd.persistance.document.UniqueIdMgd;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoxDocument extends AbstractDocument {

    @BsonProperty("weight")
    private double weight;

    @BsonProperty("boxType")
    private BoxTypeDocument boxType;

    @Builder
    @BsonCreator
    public BoxDocument(@BsonProperty("_id") UniqueIdMgd entityId,
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
