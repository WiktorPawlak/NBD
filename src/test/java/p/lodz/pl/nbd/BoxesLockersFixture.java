package p.lodz.pl.nbd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.ParcelLocker;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;

public class BoxesLockersFixture {

    public Box bundle = Box.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    Bundle.builder()
                            .fragile(true)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();

    public Box envelope = Box.builder()
            .id(UUID.randomUUID())
            .weight(10)
            .boxType(
                    Envelope.builder()
                            .priority(1)
                            .length(2)
                            .height(2)
                            .width(2)
                            .build()
            )
            .build();

    public List<Locker> fullLockers = fillLockers();

    private List<Locker> fillLockers() {
        List<Locker> lockers = new ArrayList<>(ParcelLocker.LOCKER_AMMOUNT);

        for (int i = 0; i < ParcelLocker.LOCKER_AMMOUNT; i++) {
            lockers.add(new Locker());
            lockers.get(i).setEmpty(false);
        }

        return lockers;
    }
}
