package com.proper.transport;

import com.proper.domain.Lease;

import java.util.Optional;

public class LeaseRepositoryImpl implements LeaseRepository{
    @Override
    public Optional<Lease> fetch(String id) {
        return Optional.empty();
    }
}
