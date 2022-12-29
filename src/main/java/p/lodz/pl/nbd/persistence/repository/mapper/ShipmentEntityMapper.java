package p.lodz.pl.nbd.persistence.repository.mapper;


import java.util.Collections;
import java.util.List;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;

import lombok.experimental.UtilityClass;
import p.lodz.pl.nbd.persistence.table.shipment.ShipmentByStartDate;
import p.lodz.pl.nbd.persistence.udt.box.BoxUdt;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;


@UtilityClass
public class ShipmentEntityMapper {

    public static List<ShipmentByStartDate> toShipmentsByStartDate(final List<Row> all) {
        return all.stream()
                .map(ShipmentEntityMapper::toShipmentByStartDate)
                .toList();
    }

    private static ShipmentByStartDate toShipmentByStartDate(final Row row) {
        var shipment = ShipmentByStartDate.builder()
                .id(row.getUuid("id"))
                .boxesCost(row.getDouble("boxes_cost"))
                .startDate(row.getInstant("start_date"))
                .bundles(toBundlesTypes(row.getList("bundles", UdtValue.class)))
                .envelopes(toEnvelopesTypes(row.getList("envelopes", UdtValue.class)))
                .build();
        shipment.setFinalizationDate(row.getInstant("finalization_date"));
        return shipment;
    }

    public static List<BundleUdt> toBundlesTypes(List<UdtValue> bundles) {
        if (bundles == null) {
            return Collections.emptyList();
        }
        return bundles.stream()
                .map(ShipmentEntityMapper::toBundleType)
                .toList();
    }

    public static BundleUdt toBundleType(final UdtValue udtValue) {
        return BundleUdt.builder()
                .fragile(udtValue.getBoolean("fragile"))
                .weight(udtValue.getDouble("weight"))
                .height(udtValue.getInt("height"))
                .length(udtValue.getInt("length"))
                .width(udtValue.getInt("width"))
                .build();
    }

    public static List<EnvelopeUdt> toEnvelopesTypes(List<UdtValue> envelopes) {
        if (envelopes == null) {
            return Collections.emptyList();
        }
        return envelopes.stream()
                .map(ShipmentEntityMapper::toEnvelopeType)
                .toList();
    }

    public static EnvelopeUdt toEnvelopeType(final UdtValue udtValue) {
        return EnvelopeUdt.builder()
                .priority(udtValue.getInt("priority"))
                .weight(udtValue.getDouble("weight"))
                .height(udtValue.getInt("height"))
                .length(udtValue.getInt("length"))
                .width(udtValue.getInt("width"))
                .build();
    }

    public static UdtValue toBoxUdt(final UserDefinedType type, final BoxUdt boxUdt) {
        UdtValue convertedBoxType = type.newValue()
                .setDouble("weight", boxUdt.getWeight())
                .setInt("height", boxUdt.getHeight())
                .setInt("length", boxUdt.getLength())
                .setInt("width", boxUdt.getWidth());
        if (boxUdt instanceof BundleUdt) {
            convertedBoxType = convertedBoxType.setBoolean("fragile", ((BundleUdt) boxUdt).getFragile());
        } else {
            convertedBoxType = convertedBoxType.setInt("priority", ((EnvelopeUdt) boxUdt).getPriority());
        }
        return convertedBoxType;
    }
}
