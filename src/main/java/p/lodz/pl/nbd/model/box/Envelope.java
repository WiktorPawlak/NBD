package p.lodz.pl.nbd.model.box;

import lombok.Builder;
import lombok.Getter;


public class Envelope extends BoxType {

    @Getter
    private final int priority;

    @Builder
    public Envelope(int length, int width, int height, int priority) {
        super(length, width, height);
        this.priority = priority;
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}