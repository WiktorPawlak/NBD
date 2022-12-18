package p.lodz.pl.nbd.persistence.udt.codec;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import p.lodz.pl.nbd.persistence.repository.mapper.ShipmentEntityMapper;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;

public class EnvelopeUdtCodec extends MappingCodec<UdtValue, EnvelopeUdt> {
    /**
     * Creates a new mapping codec providing support for {@code OuterT} based on an existing codec for
     * {@code InnerT}.
     *
     * @param innerCodec    The inner codec to use to handle instances of InnerT; must not be null.
     * @param outerJavaType The outer Java type; must not be null.
     */
    public EnvelopeUdtCodec(@NonNull final TypeCodec<UdtValue> innerCodec,
                            @NonNull final GenericType<EnvelopeUdt> outerJavaType) {
        super(innerCodec, outerJavaType);
    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected EnvelopeUdt innerToOuter(@Nullable final UdtValue value) {
        return value == null ? null : toEnvelopeType(value);
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable final EnvelopeUdt value) {
        return value == null ? null : ShipmentEntityMapper.toBoxUdt(getCqlType(), value);
    }

    protected EnvelopeUdt toEnvelopeType(UdtValue value) {
        return ShipmentEntityMapper.toEnvelopeType(value);
    }
}
