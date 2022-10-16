package p.lodz.pl.nbd.integration;

import p.lodz.pl.nbd.model.Box;
import p.lodz.pl.nbd.model.Bundle;
import p.lodz.pl.nbd.model.Envelope;
import p.lodz.pl.nbd.model.Locker;
import p.lodz.pl.nbd.model.ParcelLocker;

import java.util.ArrayList;
import java.util.List;

public class ParcelLockerFixture {

    public Box bundle = Box.builder()
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
