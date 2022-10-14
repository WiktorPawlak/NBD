package p.lodz.pl.nbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name = "Shipment.findArchivedShipments", query = "SELECT s from SHIPMENTS s WHERE s.ongoing = FALSE"),
})
@Entity
@Table(name = "SHIPMENTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Shipment {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    @Column(name = "LOCKER")
    private Locker locker;

    @OneToMany
    @Column(name = "BOX")
    @ToString.Exclude
    private List<Box> boxes;

    @Column(name = "BOX_COST")
    private double boxesCost;


    @Setter
    @Column(name = "ONGOING")
    private boolean ongoing;

    public Shipment(final Locker locker, final List<Box> boxes) {
        this.locker = locker;
        this.boxes = boxes;
        this.boxesCost = boxes.stream().mapToDouble(Box::getBoxCost).sum();
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
