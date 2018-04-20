package org.examples.pbk.otus.l151homework.messageSystem;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;

    public Address() {
        this.id = String.valueOf(ID_GENERATOR.incrementAndGet());
    }

    public Address(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
