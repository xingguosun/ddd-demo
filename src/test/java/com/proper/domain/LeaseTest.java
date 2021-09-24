package com.proper.domain;

import com.proper.events.ChargeAddedEvent;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import com.proper.transport.EventPublisher;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import javax.management.MXBean;

import com.proper.transport.EventPublisherImpl;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class LeaseTest {
  @Mock private EventPublisher<Event> eventEventPublisher = new EventPublisherImpl();

  private Lease lease;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    lease = new Lease();
    lease.setEventPublisher(eventEventPublisher);
  }

  @Test(expected = Exception.class)
  public void registerCharge_ifChargeHasBeenAddedBefore_fails() throws Exception {
    Charge charge = Charge.builder()
        .chargeDate(LocalDate.now())
        .amount(Money.of(CurrencyUnit.USD, 10))
        .count(1)
        .description("description")
        .type(Charge.Type.AdHoc)
        .build();

    lease.registerCharge(charge);
    lease.registerCharge(charge);
  }

  @Test
  public void registerCharge_ifChargeHasBeenAdded_fireEvent() throws Exception {
    Charge charge = Charge.builder()
        .chargeDate(LocalDate.now())
        .amount(Money.of(CurrencyUnit.USD, 10))
        .count(1)
        .description("description")
        .type(Charge.Type.AdHoc)
        .build();

    lease.registerCharge(charge);
    verify(eventEventPublisher).put(any(ChargeAddedEvent.class));
  }

  @Test
  public void getCharges_returnOneInstance_forEachCharge() throws Exception {
    Charge charge1 = Charge.builder()
        .chargeDate(LocalDate.now())
        .amount(Money.of(CurrencyUnit.USD, 10))
        .count(3)
        .description("charge1")
        .type(Charge.Type.AdHoc)
        .build();

    Charge charge2 = Charge.builder()
        .chargeDate(LocalDate.now())
        .amount(Money.of(CurrencyUnit.USD, 10))
        .count(3)
        .description("charge2")
        .type(Charge.Type.AdHoc)
        .build();

    Charge charge3 = Charge.builder()
        .chargeDate(LocalDate.now().plusMonths(2))
        .amount(Money.of(CurrencyUnit.USD, 10))
        .count(3)
        .description("charge3")
        .type(Charge.Type.AdHoc)
        .build();

    lease.registerCharge(charge1);
    lease.registerCharge(charge2);
    lease.registerCharge(charge3);

    List<ChargeInstance> instances = lease.getCharges(DateInterval.from(LocalDate.now(), Duration.ofDays(30)));
    assertThat(instances).hasSize(2);
    assertThat(instances).anyMatch(elem -> elem.getDescription().equals("charge1"));
    assertThat(instances).anyMatch(elem -> elem.getDescription().equals("charge2"));
  }
}
