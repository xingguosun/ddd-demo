package com.proper.transport;

import com.proper.domain.Charge;
import com.proper.domain.ChargeInstance;
import com.proper.lang.DateInterval;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChargeRepositoryImpl implements ChargeRepository{
    List<ChargeInstance> storedCharges = new ArrayList<>(); //simulate database

    @Override
    public Optional<ChargeInstance> fetch(String id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public ChargeInstance save(Charge charge) {
        ChargeInstance chargeInstance = charge.toChargeInstance();
        storedCharges.add(chargeInstance);
        return chargeInstance;
    }

    @Override
    public Boolean exist(Charge charge) {
        return storedCharges.contains(charge.toChargeInstance());
    }

    @Override
    public List<ChargeInstance> filterByDateInterval(DateInterval interval) {
        return storedCharges.stream().filter(
                (ChargeInstance c) ->
                        c.getChargeDate().equals(interval.getStart()) ||
                                (c.getChargeDate().isAfter(interval.getStart()) &&
                        c.getChargeDate().isBefore(interval.getStart().plusDays((interval.getDuration().toDays())))))
                .collect(Collectors.toList());
    }

}
