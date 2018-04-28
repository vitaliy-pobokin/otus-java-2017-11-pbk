package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSystemContext {

    private final Address messageSystemAddress;
    private final ConcurrentMap<String, Address> services;

    public MessageSystemContext(Address messageSystemAddress) {
        this.messageSystemAddress = messageSystemAddress;
        this.services = new ConcurrentHashMap<>();
    }

    public Address getMessageSystemAddress() {
        return messageSystemAddress;
    }

    public void registerService(String serviceName, Address address) {
        services.put(serviceName, address);
    }

    public Address getServiceAddress(String serviceName) {
        return services.get(serviceName);
    }
}
