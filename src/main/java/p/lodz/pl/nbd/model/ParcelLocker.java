package p.lodz.pl.nbd.model;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import p.lodz.pl.nbd.manager.ShipmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor(onConstructor = @__(@Inject))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParcelLocker {

    private static final int LOCKER_AMMOUNT = 20;

    private List<Locker> lockers;

    private ShipmentManager shipmentManager;

    @PostConstruct
    private void initialize() {
        lockers = new ArrayList<>(LOCKER_AMMOUNT);
        for (int i = 0; i < LOCKER_AMMOUNT; i++) {
            lockers.add(new Locker());
        }
    }

    public void sendPackage(final Box box) throws Exception {
        var emptyLockers = lockers.stream()
                .filter(Locker::isEmpty)
                .collect(Collectors.toList());

        if (emptyLockers.isEmpty()) {
            throw new Exception("Nie ma wolnych lockerow");
        }

        emptyLockers.get(0).setPassword(UUID.randomUUID().toString());
        shipmentManager.addShipment(emptyLockers.get(0), List.of(box));
        emptyLockers.get(0).setEmpty(false);
    }

    public void receivePackage(final String code, final String shipmentId) throws Exception {
        var locker = lockers.stream()
                .filter(lc -> lc.checkPassword(code))
                .collect(Collectors.toList());

        if (locker.isEmpty()) {
            throw new Exception("Nie ma takiego lockera");
        }

        shipmentManager.getShipment(UUID.fromString(shipmentId)).setOngoing(false);
        locker.get(0)
                .setEmpty(true);
        locker.get(0)
                .setPassword("");
        shipmentManager.finalizeShipment(UUID.fromString(shipmentId));
    }

    public long countEmptyLockers() {
        return lockers.stream()
                .filter(Locker::isEmpty)
                .count();
    }
}
