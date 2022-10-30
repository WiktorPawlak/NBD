package p.lodz.pl.nbd.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UniqueIdMgd {

    private UUID uuid;

    public UniqueIdMgd() {
        this.uuid = UUID.randomUUID();
    }
}
