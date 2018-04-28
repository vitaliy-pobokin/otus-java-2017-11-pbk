package org.examples.pbk.otus.l71homework;

public class Banknote {
    private final BanknoteType type;
    private final String serialNumber;

    Banknote(BanknoteType type, String serialNumber) {
        this.type = type;
        this.serialNumber = serialNumber;
    }

    BanknoteType getType() {
        return type;
    }

    String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "-------------\n" +
                "|\t" + type.getNominal() + "   \t|\t-\t" + serialNumber + "\n" +
                "-------------\n"
                ;
    }
}
