package p.lodz.pl.nbd.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUNDLE")
@DiscriminatorValue("bundle")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Bundle extends BoxType {

    @Getter
    private boolean fragile;

    @Override
    public double getCostModifier() {
        return 2;
    }
}
