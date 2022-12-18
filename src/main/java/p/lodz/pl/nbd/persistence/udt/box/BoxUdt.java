package p.lodz.pl.nbd.persistence.udt.box;


import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class BoxUdt implements Serializable {

    private double weight;

    private int length;

    private int width;

    private int height;

    public double getBoxCost() {
        return weight * getCostModifier();
    }

    public abstract double getCostModifier();
}
