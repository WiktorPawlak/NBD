package p.lodz.pl.nbd.model.box;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class Bundle extends Box {

    @Getter
    private final Boolean fragile;

    @Builder(builderMethodName = "lombokBuilder", builderClassName = "LombokBuilder", buildMethodName = "lombokBuild")
    public Bundle(double weight,
                  int length,
                  int width,
                  int height,
                  Boolean fragile) {
        super(weight, length, width, height);
        this.fragile = fragile;
    }

    @Override
    public double getCostModifier() {
        return 2;
    }
}
