package p.lodz.pl.nbd.persistence.udt.codec;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import p.lodz.pl.nbd.persistence.repository.mapper.ShipmentEntityMapper;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;

public class BundleUdtCodec extends MappingCodec<UdtValue, BundleUdt> {
    /**
     * Creates a new mapping codec providing support for {@code OuterT} based on an existing codec for
     * {@code InnerT}.
     *
     * @param innerCodec    The inner codec to use to handle instances of InnerT; must not be null.
     * @param outerJavaType The outer Java type; must not be null.
     */
    public BundleUdtCodec(@NonNull final TypeCodec<UdtValue> innerCodec,
                          @NonNull final GenericType<BundleUdt> outerJavaType) {
        super(innerCodec, outerJavaType);
    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected BundleUdt innerToOuter(@Nullable final UdtValue value) {
        return value == null ? null : ShipmentEntityMapper.toBundleType(value);
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable final BundleUdt value) {
        return value == null ? null : ShipmentEntityMapper.toBoxUdt(getCqlType(), value);
    }
}