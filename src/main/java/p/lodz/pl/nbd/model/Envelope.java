package p.lodz.pl.nbd.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ENVELOPE")
@DiscriminatorValue("bundle")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Envelope extends BoxType {

    private int priority;

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
