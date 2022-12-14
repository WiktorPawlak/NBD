package p.lodz.pl.nbd.persistence.document.box;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@CqlName("bundle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BundleDocument extends BoxTypeDocument {

    @Getter
    private Boolean fragile;

    @Builder
    public BundleDocument(UUID id,
                          String discriminator,
                          int length,
                          int width,
                          int height,
                          Boolean fragile) {
        super(id, discriminator, length, width, height);
        this.fragile = fragile;
    }

    @Override
    @PartitionKey
    public UUID getId() {
        return super.getId();
    }

    @Override
    public double getCostModifier() {
        return 2;
    }
}
