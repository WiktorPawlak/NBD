package p.lodz.pl.nbd.model;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import p.lodz.pl.nbd.model.box.Box;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Shipment {

    private UUID id;

    @Setter
    private Locker locker;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Box> boxes;

    private double boxesCost;

    @Setter
    private boolean ongoing;

    @Builder
    public Shipment(final UUID id, final Locker locker, final List<Box> boxes) {
        super();
        this.id = id;
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(Box::getBoxCost).sum();
    }
}
