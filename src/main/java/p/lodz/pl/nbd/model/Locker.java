package p.lodz.pl.nbd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Locker {

    private Boolean empty = true;

    private String password;

    public boolean checkPassword(final String passwd) {
        return passwd.equals(password);
    }
}
