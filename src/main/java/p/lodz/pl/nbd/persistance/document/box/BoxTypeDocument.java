package p.lodz.pl.nbd.persistance.document.box;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
public abstract class BoxTypeDocument implements Serializable {

    @BsonProperty("length")
    private int length;

    @BsonProperty("width")
    private int width;

    @BsonProperty("height")
    private int height;

    public abstract double getCostModifier();
}
