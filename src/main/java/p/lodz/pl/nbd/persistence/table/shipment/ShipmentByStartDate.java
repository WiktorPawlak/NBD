package p.lodz.pl.nbd.persistence.table.shipment;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;


@Entity
@CqlName("shipments_by_start_date")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentByStartDate {

    @PartitionKey
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BundleUdt> bundles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EnvelopeUdt> envelopes;

    private double boxesCost;

    @ClusteringColumn
    private Instant startDate;

    private Instant finalizationDate;

    @Builder
    public ShipmentByStartDate(final UUID id,
                               final List<BundleUdt> bundles,
                               final List<EnvelopeUdt> envelopes,
                               final double boxesCost,
                               final Instant startDate) {
        this.id = id;
        this.bundles = bundles;
        this.envelopes = envelopes;
        this.boxesCost = boxesCost;
        this.startDate = startDate;
    }
}
