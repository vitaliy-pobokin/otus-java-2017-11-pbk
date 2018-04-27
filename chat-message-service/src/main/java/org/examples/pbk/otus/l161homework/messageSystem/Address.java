package org.examples.pbk.otus.l161homework.messageSystem;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

public final class Address implements Serializable {
    private static final long serialVersionUID = -7820656975615542537L;
    private final InetAddress inetAddress;
    private final int port;

    public Address(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return port == address.port &&
                inetAddress.equals(address.inetAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inetAddress, port);
    }

    @Override
    public String toString() {
        return "Address{" +
                "inetAddress=" + inetAddress +
                ", port=" + port +
                '}';
    }
}
