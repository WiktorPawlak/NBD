package p.lodz.pl.nbd.persistance.document.box;

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
import p.lodz.pl.nbd.persistance.document.AbstractDocument;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BoxDocument extends AbstractDocument {

    @BsonProperty("weight")
    @JsonbProperty("weight")
    private double weight;

    @BsonProperty(value = "boxType", useDiscriminator = true)
    @JsonbProperty("boxType")
    private BoxTypeDocument boxType;

    @Builder
    @BsonCreator
    @JsonbCreator
    public BoxDocument(@JsonbProperty("id") @BsonProperty("id") UUID id,
                       @JsonbProperty("weight") @BsonProperty("weight") double weight,
                       @JsonbProperty("boxType") @BsonProperty("boxType") BoxTypeDocument boxType) {
        super(id);
        this.weight = weight;
        this.boxType = boxType;
    }

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
