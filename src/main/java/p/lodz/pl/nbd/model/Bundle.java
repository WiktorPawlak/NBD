package p.lodz.pl.nbd.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Bundle extends BoxType {

    @Getter
    private boolean fragile;

    public Bundle(int length, int width, int height, boolean fragile) {
        super(length, width, height);
        this.fragile = fragile;
    }

    @Override
    public double getCostModifier() {
        return 1;
    }
}
