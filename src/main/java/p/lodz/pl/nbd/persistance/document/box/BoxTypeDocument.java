package p.lodz.pl.nbd.persistance.document.box;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@BsonDiscriminator(key = "_clazz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@JsonbTypeInfo(key = "@discriminator", value = {
        @JsonbSubtype(alias = "Bundle", type = BundleDocument.class),
        @JsonbSubtype(alias = "Envelope", type = EnvelopeDocument.class)
})
public abstract class BoxTypeDocument implements Serializable {

    @BsonProperty("length")
    @JsonbProperty("length")
    private int length;

    @BsonProperty("width")
    @JsonbProperty("width")
    private int width;

    @BsonProperty("height")
    @JsonbProperty("height")
    private int height;

    public abstract double getCostModifier();
}
