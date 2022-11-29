package p.lodz.pl.nbd.persistance.document.box;

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


@BsonDiscriminator(key = "_clazz", value = "Envelope")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class EnvelopeDocument extends BoxTypeDocument {

    @Getter
    @BsonProperty("priority")
    @JsonbProperty("priority")
    private int priority;

    @BsonCreator
    @Builder
    @JsonbCreator
    public EnvelopeDocument(@JsonbProperty("length") @BsonProperty("length") int length,
                            @JsonbProperty("width") @BsonProperty("width") int width,
                            @JsonbProperty("height") @BsonProperty("height") int height,
                            @JsonbProperty("priority") @BsonProperty("priority") int priority) {
        super(length, width, height);
        this.priority = priority;
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
