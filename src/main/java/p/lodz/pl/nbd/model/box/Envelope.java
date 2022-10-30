package p.lodz.pl.nbd.model.box;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Entity
@Table(name = "ENVELOPE")
@DiscriminatorValue("bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@BsonDiscriminator(value = "envelope")
public class Envelope extends BoxType {

    @BsonProperty("priority")
    private int priority;

    /*
    @Builder
    public Envelope(UUID id, int length, int width, int height, int priority) {
        super(id, length, width, height);
        this.priority = priority;
    }
    */

    @BsonCreator
    @Builder
    public Envelope(@BsonProperty("id") UUID id,
                    @BsonProperty("length") int length,
                    @BsonProperty("width") int width,
                    @BsonProperty("height") int height,
                    @BsonProperty("priority")int priority) {
        super(id, length, width, height);
        this.priority = priority;
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
