package p.lodz.pl.nbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@NamedQuery(name = "Shipment.findArchivedShipments",
        query = "SELECT s from Shipment s WHERE s.ongoing = FALSE")
@Entity
@Table(name = "SHIPMENTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Shipment {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Embedded
    private Locker locker;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Box> boxes;

    @Column(name = "BOX_COST")
    private double boxesCost;

    @Setter
    @Column(name = "ONGOING")
    private boolean ongoing;

    @Builder
    public Shipment(final Locker locker, final List<Box> boxes) {
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(Box::getBoxCost).sum();
    }
}
