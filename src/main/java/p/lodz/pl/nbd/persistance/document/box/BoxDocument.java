package p.lodz.pl.nbd.persistance.document.box;

import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.persistance.document.AbstractDocument;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BoxDocument extends AbstractDocument {

    @BsonProperty("weight")
    private double weight;

    @BsonProperty(value = "boxType", useDiscriminator = true)
    private BoxTypeDocument boxType;

    @Builder
    @BsonCreator
    public BoxDocument(@BsonProperty("id") UUID id,
                       @BsonProperty("weight") double weight,
                       @BsonProperty("boxType") BoxTypeDocument boxType) {
        super(id);
        this.weight = weight;
        this.boxType = boxType;
    }

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
