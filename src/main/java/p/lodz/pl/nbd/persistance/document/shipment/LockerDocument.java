package p.lodz.pl.nbd.persistance.document.shipment;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LockerDocument implements Serializable {

    @BsonProperty("empty")
    private Boolean empty = true;

    @BsonProperty("password")
    private String password;

    @BsonCreator
    @Builder
    public LockerDocument(@BsonProperty("empty") Boolean empty,
                          @BsonProperty("password") String password) {
        this.empty = empty;
        this.password = password;
    }

    public boolean checkPassword(final String passwd) {
        return passwd.equals(password);
    }
}