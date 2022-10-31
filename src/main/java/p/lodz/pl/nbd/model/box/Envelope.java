package p.lodz.pl.nbd.model.box;

import lombok.Builder;


public class Envelope extends BoxType {

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
