package p.lodz.pl.nbd.mongo;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@MappedSuperclass
@Getter
@AllArgsConstructor
public abstract class AbstractEntityMgd implements Serializable {

    @BsonProperty("_id")
    private final UniqueIdMgd entityId;


}
