package p.lodz.pl.nbd.persistance.document;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UniqueIdMgd {

    private final UUID uuid;

    public UniqueIdMgd() {
        this.uuid = UUID.randomUUID();
    }

    public UniqueIdMgd(UUID id) {
        uuid = id;
    }
}
