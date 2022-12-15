package p.lodz.pl.nbd.persistence.repository;


import java.net.InetSocketAddress;

import com.datastax.oss.driver.api.core.addresstranslation.AddressTranslator;
import com.datastax.oss.driver.api.core.context.DriverContext;

import edu.umd.cs.findbugs.annotations.NonNull;


public class NdbAddressTranslator implements AddressTranslator {

    public NdbAddressTranslator(DriverContext ctx) {
        int lol = 1;
    }

    @NonNull
    @Override
    public InetSocketAddress translate(@NonNull final InetSocketAddress inetSocketAddress) {
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();
        String hostName = inetSocketAddress.getHostName();
        return switch (hostAddress) {
            case "10.5.0.10" -> new InetSocketAddress("localhost", 9042);
            case "10.5.0.20" -> new InetSocketAddress("localhost", 9043);
            default -> throw new RuntimeException("wrong address");
        };

//        InetSocketAddress translatedAddress = null;
//
//        if (inetSocketAddress.getHostName().equals("cassandra1")) {
//            translatedAddress = new InetSocketAddress("localhost", 9042);
//        } else if (inetSocketAddress.getHostName().equals("cassandra2")) {
//            translatedAddress = new InetSocketAddress("localhost", 9043);
//        }
//        return translatedAddress;
    }

    @Override
    public void close() {

    }
}
