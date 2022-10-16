package p.lodz.pl.nbd.model.box;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "BUNDLE")
@DiscriminatorValue("bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Bundle extends BoxType {

    @Getter
    private boolean fragile;

    @Builder
    public Bundle(UUID id, int length, int width, int height, boolean fragile) {
        super(id, length, width, height);
        this.fragile = fragile;
    }

    @Override
    public double getCostModifier() {
        return 2;
    }
}
