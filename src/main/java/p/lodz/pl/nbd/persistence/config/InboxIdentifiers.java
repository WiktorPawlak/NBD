package p.lodz.pl.nbd.persistence.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;

import lombok.experimental.UtilityClass;


@UtilityClass
public class InboxIdentifiers {
    public static final CqlIdentifier NBD_INBOX = CqlIdentifier.fromCql("nbd_inbox");

    public static final String BUNDLE_TYPE = "bundle_type";
    public static final String ENVELOPE_TYPE = "envelope_type";
    public static final String WEIGHT = "weight";
    public static final String LENGTH = "length";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String FRAGILE = "fragile";
    public static final String PRIORITY = "priority";
    public static final String SHIPMENTS_BY_START_DATE = "shipments_by_start_date";
    public static final String ID = "id";
    public static final String BOXES_COST = "boxes_cost";
    public static final String START_DATE = "start_date";
    public static final String FINALIZATION_DATE = "finalization_date";
    public static final String BUNDLES = "bundles";
    public static final String ENVELOPES = "envelopes";
    public static final String UDT_DEFINITION_NOT_FOUND = "UDT DEFINITION NOT FOUND";
}
