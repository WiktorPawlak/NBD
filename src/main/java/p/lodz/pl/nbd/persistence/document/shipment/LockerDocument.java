package p.lodz.pl.nbd.persistence.document.shipment;

import java.io.Serializable;

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

    private Boolean empty = true;

    private String password;

    @Builder
    public LockerDocument(Boolean empty, String password) {
        this.empty = empty;
        this.password = password;
    }

    public boolean checkPassword(final String passwd) {
        return passwd.equals(password);
    }
}