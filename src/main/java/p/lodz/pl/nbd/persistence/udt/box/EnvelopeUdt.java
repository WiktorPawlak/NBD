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
@CqlName("envelope_type")
@SchemaHint(targetElement = UDT)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class EnvelopeUdt extends BoxUdt {

    @Getter
    @Setter
    private int priority;

    @Builder
    public EnvelopeUdt(double weight,
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
