package p.lodz.pl.nbd.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Shipment {

    private UUID id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Bundle> bundles;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Envelope> envelopes;
    private double boxesCost;
    @Setter
    private Instant startDate = Instant.now();
    @Setter
    private Instant finalizationDate;

    @Builder
    public Shipment(final UUID id,
                    final List<Bundle> bundles,
                    final List<Envelope> envelopes) {
        this.id = id;
        this.bundles = bundles;
        this.envelopes = envelopes;
        this.boxesCost = calculateTotalCost(bundles, envelopes);
    }

    private double calculateTotalCost(final List<Bundle> bundles,
                                      final List<Envelope> envelopes) {
        double total = 0;
        if (bundles != null) {
            total += bundles.stream().mapToDouble(Bundle::getBoxCost).sum();
        }
        if (envelopes != null) {
            total += envelopes.stream().mapToDouble(Envelope::getBoxCost).sum();
        }
        return total;
    }
}
