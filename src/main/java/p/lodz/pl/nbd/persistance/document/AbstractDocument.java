package p.lodz.pl.nbd.persistance.document;

import java.io.Serializable;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDocument implements Serializable {

    @BsonProperty("_id")
    private UUID uuid;

    @BsonCreator
    protected AbstractDocument(@BsonProperty("_id") UUID entityId) {
        this.uuid = entityId;
    }
}
