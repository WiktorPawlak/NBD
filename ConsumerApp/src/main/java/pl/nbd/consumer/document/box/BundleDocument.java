package pl.nbd.consumer.document.box;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@BsonDiscriminator(key = "_clazz", value = "Bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BundleDocument extends BoxTypeDocument {

    @Getter
    @BsonProperty("fragile")
    @JsonbProperty("fragile")
    private Boolean fragile;

    @BsonCreator
    @Builder
    @JsonbCreator
    public BundleDocument(@JsonbProperty("length") @BsonProperty("length") int length,
                          @JsonbProperty("width") @BsonProperty("width") int width,
                          @JsonbProperty("height") @BsonProperty("height") int height,
                          @JsonbProperty("fragile") @BsonProperty("fragile") Boolean fragile) {
        super(length, width, height);
        this.fragile = fragile;
    }


    @Override
    public double getCostModifier() {
        return 2;
    }
}
