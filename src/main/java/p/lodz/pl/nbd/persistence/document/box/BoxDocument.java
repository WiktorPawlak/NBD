package p.lodz.pl.nbd.persistence.document.box;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BoxDocument {

    private UUID id;

    private double weight;

    private BoxTypeDocument boxType;

    @Builder
    public BoxDocument(UUID id,
                       double weight,
                       BoxTypeDocument boxType) {
        this.id = id;
        this.weight = weight;
        this.boxType = boxType;
    }

    public double getBoxCost() {
        return weight * boxType.getCostModifier();
    }
}
