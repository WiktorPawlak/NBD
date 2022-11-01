package p.lodz.pl.nbd.model.box;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class Bundle extends BoxType {

    @Getter
    private final Boolean fragile;

    @Builder
    public Bundle(int length, int width, int height, Boolean fragile) {
        super(length, width, height);
        this.fragile = fragile;
    }

    @Override
    public double getCostModifier() {
        return 2;
    }
}
