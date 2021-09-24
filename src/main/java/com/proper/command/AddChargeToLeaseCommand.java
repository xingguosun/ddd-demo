package com.proper.command;

import com.proper.domain.Charge;
import com.proper.domain.Lease;
import com.proper.transport.LeaseRepository;
import com.proper.transport.LeaseRepositoryImpl;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

public class AddChargeToLeaseCommand {

    public Response execute(Request request) {
        //TODO: Fetch the lease from the repository
        LeaseRepository leaseRepository = new LeaseRepositoryImpl();
        Optional<Lease> lease = leaseRepository.fetch(request.getLeaseId());

        //TODO: Add the charge
        if (!lease.isPresent()) {
            return Response.builder().status(Status.status.Failed).msg("Lease dose not exist.").build();
        }
        try {
            lease.get().registerCharge(request.getCharge());
        } catch (Exception e) {
            return Response.builder().status(Status.status.Failed).msg("Charge registration failed.").build();
        }
        //TODO: Return response
        return Response.builder().status(Status.status.Successful).msg("Successful.").build();
    }

    @Value
    @Builder
    public static class Request {
        String leaseId;
        Charge charge;
    }

    @Value
    @Builder
    public static class Response {
        Status.status status;
        String msg;
    }

    public static class Status {
        enum status {Successful, Failed}
    }
}
