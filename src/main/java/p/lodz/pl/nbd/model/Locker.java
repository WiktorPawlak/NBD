package p.lodz.pl.nbd.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Locker implements Serializable {

    private boolean empty = true;
    private String password;

    public boolean checkPassword(final String passwd) {
        return passwd.equals(password);
    }
}
