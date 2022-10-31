package p.lodz.pl.nbd.persistance.document;

import java.io.Serializable;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDocument implements Serializable {
    
    @BsonId
    private UUID id;

    @BsonCreator
    protected AbstractDocument(@BsonProperty("id") UUID id) {
        this.id = id;
    }
}
