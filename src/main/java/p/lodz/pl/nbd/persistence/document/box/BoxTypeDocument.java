package p.lodz.pl.nbd.persistence.document.box;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public abstract class BoxTypeDocument implements Serializable {

    @PartitionKey
    protected UUID id;

    private String discriminator;

    private int length;

    private int width;

    private int height;

    public abstract UUID getId();

    public abstract double getCostModifier();
}
