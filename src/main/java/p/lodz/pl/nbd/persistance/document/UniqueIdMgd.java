package p.lodz.pl.nbd.persistance.document;

import java.io.Serializable;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UniqueIdMgd implements Serializable {

    @BsonProperty("_id")
    private final UUID uuid;

    public UniqueIdMgd() {
        this.uuid = UUID.randomUUID();
    }

    public UniqueIdMgd(UUID id) {
        uuid = id;
    }
}
