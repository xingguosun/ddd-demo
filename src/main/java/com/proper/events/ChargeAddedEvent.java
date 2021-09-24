package com.proper.events;

import com.proper.domain.Charge;

public class ChargeAddedEvent extends Event {
    //TODO: Add charge information
    private final Charge charge;

    public ChargeAddedEvent(Charge charge) {
        this.charge = charge;
    }

    public Charge getCharge() {
        return charge;
    }
}
