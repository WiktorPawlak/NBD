package p.lodz.pl.nbd.persistence.document.shipment;

import java.io.Serializable;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@CqlName("locker")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LockerDocument implements Serializable {

    @Column(name = "locker_empty")
    private Boolean empty = true;

    @Column(name = "locker_password")
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