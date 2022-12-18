package p.lodz.pl.nbd.persistence.config;


import java.net.InetSocketAddress;

import com.datastax.oss.driver.api.core.addresstranslation.AddressTranslator;
import com.datastax.oss.driver.api.core.context.DriverContext;

import edu.umd.cs.findbugs.annotations.NonNull;


public class NdbAddressTranslator implements AddressTranslator {

    public NdbAddressTranslator(DriverContext ctx) {

    }

    @NonNull
    @Override
    public InetSocketAddress translate(@NonNull final InetSocketAddress inetSocketAddress) {
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();
        return switch (hostAddress) {
            case "10.5.0.10" -> new InetSocketAddress("localhost", 9042);
            case "10.5.0.20" -> new InetSocketAddress("localhost", 9043);
//            case "10.5.0.30" -> new InetSocketAddress("localhost", 9044);
            default -> throw new RuntimeException("wrong address");
        };
    }

    @Override
    public void close() {

    }
}
