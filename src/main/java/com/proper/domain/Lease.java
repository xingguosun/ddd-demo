package com.proper.domain;

import com.proper.events.ChargeAddedEvent;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import com.proper.transport.ChargeRepositoryImpl;
import com.proper.transport.EventPublisher;
import com.proper.transport.EventPublisherImpl;
import com.proper.transport.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class Lease {
  private String id;
  private List<Tenant> tenants;
  private EventPublisher<Event> eventPublisher;
  private final ChargeRepositoryImpl chargeRepository = new ChargeRepositoryImpl();

  public void registerCharge(Charge charge) throws Exception {
    //TODO: register the charge
    //TODO: Make sure the charge has not been added
    if (chargeRepository.exist(charge)) {
        throw new Exception();
    }
    chargeRepository.save(charge);

    //TODO: Broadcast the charge has been registered (EventPublisher)
    ChargeAddedEvent chargeAddedEvent = new ChargeAddedEvent(charge);
    eventPublisher.put(chargeAddedEvent);
  }

  public List<ChargeInstance> getCharges(DateInterval interval) {
    //TODO: return 1 ChargeInstance for each charge falling within the interval
    return chargeRepository.filterByDateInterval(interval);
  }

  public String getId() {
    return id;
  }

  protected void setId(String id) {
    this.id = id;
  }

  public List<Tenant> getTenants() {
    return tenants;
  }

  public void setTenants(List<Tenant> tenants) {
    this.tenants = tenants;
  }

  public void setEventPublisher(EventPublisher<Event> eventPublisher) {
    this.eventPublisher = eventPublisher;
  }
}
