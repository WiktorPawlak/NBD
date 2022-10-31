package p.lodz.pl.nbd.model.box;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class Box {

    private UUID id;

    private double weight;

    private BoxType boxType;

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
