package p.lodz.pl.nbd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import p.lodz.pl.nbd.manager.ShipmentManager;
import p.lodz.pl.nbd.model.box.Box;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParcelLocker {

    @Getter
    public static final int LOCKER_AMMOUNT = 20;

    @Setter
    private List<Locker> lockers;

    private ShipmentManager shipmentManager;

    @Builder
    public ParcelLocker(ShipmentManager shipmentManager) {
        this.shipmentManager = shipmentManager;
        lockers = new ArrayList<>(LOCKER_AMMOUNT);
        for (int i = 0; i < LOCKER_AMMOUNT; i++) {
            lockers.add(new Locker());
        }
    }

    public void sendPackage(final Box box) throws Throwable {
        var emptyLockers = lockers.stream()
                .filter(Locker::getEmpty)
                .collect(Collectors.toList());

        if (emptyLockers.isEmpty()) {
            throw new Exception("Nie ma wolnych lockerow");
        }

        emptyLockers.get(0).setPassword(UUID.randomUUID().toString());
        emptyLockers.get(0).setEmpty(false);
        shipmentManager.addShipment(emptyLockers.get(0), List.of(box));
    }

    public void receivePackage(final String code, final UUID shipmentId) throws Throwable {
        var locker = lockers.stream()
                .filter(lc -> lc.checkPassword(code))
                .collect(Collectors.toList());

        if (locker.isEmpty()) {
            throw new Exception("Nie ma takiego lockera");
        }

        shipmentManager.getShipment(shipmentId).setOngoing(false);
        locker.get(0)
                .setEmpty(true);
        locker.get(0)
                .setPassword("");
        shipmentManager.finalizeShipment(shipmentId);
    }

    public long countEmptyLockers() {
        return lockers.stream()
                .filter(Locker::getEmpty)
                .count();
    }
}
