package p.lodz.pl.nbd.model;

import jakarta.inject.Inject;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Embeddable
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(onConstructor = @__(@Inject))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public abstract class BoxType {

    private int length;

    private int width;

    private int height;

    public abstract double getCostModifier();
}
