package p.lodz.pl.nbd.model.box;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Box {

    private UUID id;

    private double weight;

    private BoxType boxType;

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
