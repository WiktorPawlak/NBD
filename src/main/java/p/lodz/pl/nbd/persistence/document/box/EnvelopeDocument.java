package p.lodz.pl.nbd.persistence.document.box;

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


@Entity
@CqlName("envelope")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class EnvelopeDocument extends BoxTypeDocument {

    @Getter
    @ClusteringColumn
    private int priority;

    @Builder
    public EnvelopeDocument(UUID id,
                            String discriminator,
                            int length,
                            int width,
                            int height,
                            int priority) {
        super(id, discriminator, length, width, height);
        this.priority = priority;
    }

    @Override
    @PartitionKey
    public UUID getId() {
        return super.getId();
    }

    @Override
    public double getCostModifier() {
        return 1d + Math.pow(2, priority + 1d);
    }
}
