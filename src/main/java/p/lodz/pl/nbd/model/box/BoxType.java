package p.lodz.pl.nbd.model.box;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
public abstract class BoxType {

    private int length;

    private int width;

    private int height;

    public abstract double getCostModifier();
}
