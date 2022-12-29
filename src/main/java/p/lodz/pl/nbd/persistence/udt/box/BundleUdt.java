package p.lodz.pl.nbd.persistence.udt.box;

import static com.datastax.oss.driver.api.mapper.annotations.SchemaHint.TargetElement.UDT;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.SchemaHint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@CqlName("bundle_type")
@SchemaHint(targetElement = UDT)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BundleUdt extends BoxUdt {

    @Getter
    @Setter
    private Boolean fragile;

    @Builder
    public BundleUdt(double weight,
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
