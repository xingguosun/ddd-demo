package com.proper.reaction;

import com.proper.domain.ChargeInstance;
import com.proper.domain.Lease;
import com.proper.domain.Tenant;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import com.proper.transport.EmailException;
import com.proper.transport.EmailSender;
import com.proper.transport.EmailSenderImpl;
import com.proper.transport.EventPublisherImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ChargeAddedToLeaseReaction {
    private final EventPublisherImpl eventPublisher;
    private final Lease lease;

    public ChargeAddedToLeaseReaction(EventPublisherImpl eventPublisher, Lease lease) {
        this.eventPublisher = eventPublisher;
        this.lease = lease;
    }

    //TODO: Poll 1 event
    //TODO: Fetch all charges instances from the Lease for the next 30 days
    //TODO: Notify the tenants with an email
    public void react() throws EmailException {
        Event event = eventPublisher.poll();
        List<ChargeInstance> chargeInstances = lease.getCharges(DateInterval.from(LocalDate.now(), Duration.ofDays(30)));
        EmailSender emailSender = new EmailSenderImpl();
        emailSender.send(
                lease.getTenants().stream().map(Tenant::getEmail).collect(Collectors.joining()),
                "example@mail.com",
                chargeInstances.toString());
    }
}
