package p.lodz.pl.nbd.persistance.document.box;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


@BsonDiscriminator(key = "_clazz", value = "Envelope")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnvelopeDocument extends BoxTypeDocument {

    @BsonProperty("priority")
    private int priority;

    @BsonCreator
    @Builder
    public EnvelopeDocument(@BsonProperty("length") int length,
                            @BsonProperty("width") int width,
                            @BsonProperty("height") int height,
                            @BsonProperty("priority") int priority) {
        super(length, width, height);
        this.priority = priority;
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
