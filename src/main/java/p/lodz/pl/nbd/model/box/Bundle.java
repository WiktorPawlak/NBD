package p.lodz.pl.nbd.model.box;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Entity
@Table(name = "BUNDLE")
@DiscriminatorValue("bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@BsonDiscriminator(value = "Bundle")
public class Bundle extends BoxType {

    @Getter
    @BsonProperty("fragile")
    private boolean fragile;

    /*
    @Builder
    public Bundle(UUID id, int length, int width, int height, boolean fragile) {
        super(id, length, width, height);
        this.fragile = fragile;
    }
    */

    @BsonCreator
    @Builder
    public Bundle(@BsonProperty("id") UUID id,
                  @BsonProperty("length") int length,
                  @BsonProperty("width") int width,
                  @BsonProperty("height") int height,
                  @BsonProperty("fragile") boolean fragile) {
        super(id, length, width, height);
        this.fragile = fragile;
    }


    @Override
    public double getCostModifier() {
        return 2;
    }
}
