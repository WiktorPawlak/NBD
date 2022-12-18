package p.lodz.pl.nbd.model.box;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class Envelope extends Box {

    @Getter
    private final int priority;

    @Builder(builderMethodName = "lombokBuilder", builderClassName = "LombokBuilder", buildMethodName = "lombokBuild")
    public Envelope(double weight,
                    int length,
                    int width,
                    int height,
                    int priority) {
        super(weight, length, width, height);
        this.priority = priority;
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
