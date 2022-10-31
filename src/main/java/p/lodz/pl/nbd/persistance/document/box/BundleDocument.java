package p.lodz.pl.nbd.persistance.document.box;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@BsonDiscriminator(key = "_clazz", value = "Bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BundleDocument extends BoxTypeDocument {

    @Getter
    @BsonProperty("fragile")
    private Boolean fragile;

    @BsonCreator
    @Builder
    public BundleDocument(@BsonProperty("length") int length,
                          @BsonProperty("width") int width,
                          @BsonProperty("height") int height,
                          @BsonProperty("fragile") Boolean fragile) {
        super(length, width, height);
        this.fragile = fragile;
    }


    @Override
    public double getCostModifier() {
        return 2;
    }
}
