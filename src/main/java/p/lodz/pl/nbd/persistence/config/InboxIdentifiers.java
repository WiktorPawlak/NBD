package p.lodz.pl.nbd.persistence.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;

import lombok.experimental.UtilityClass;


@UtilityClass
public class InboxIdentifiers {
    public static final CqlIdentifier NBD_INBOX = CqlIdentifier.fromCql("nbd_inbox");
    public static final CqlIdentifier ID = CqlIdentifier.fromCql("id");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier LENGTH = CqlIdentifier.fromCql("length");
    public static final CqlIdentifier WIDTH = CqlIdentifier.fromCql("width");
    public static final CqlIdentifier HEIGHT = CqlIdentifier.fromCql("height");
    public static final CqlIdentifier FRAGILE = CqlIdentifier.fromCql("fragile");
    public static final CqlIdentifier PRIORITY = CqlIdentifier.fromCql("priority");

}
