package p.lodz.pl.nbd.persistence.document.box;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class BoxTypeDocument implements Serializable {

    private UUID id;

    private String discriminator;

    private int length;

    private int width;

    private int height;

    public abstract double getCostModifier();
}
