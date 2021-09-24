package com.proper.transport;

import com.proper.domain.Charge;
import com.proper.domain.ChargeInstance;
import com.proper.lang.DateInterval;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository {
    Optional<ChargeInstance> fetch(String id) throws RepositoryException;
    ChargeInstance save(Charge charge) throws Exception;
    Boolean exist(Charge charge) throws Exception;
    List<ChargeInstance> filterByDateInterval(DateInterval interval) throws RepositoryException;
}
