package org.examples.pbk.otus.l71homework;

public enum BanknoteType {
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private int nominal;

    private BanknoteType(int nominal) {
        this.nominal = nominal;
    }

    public Integer getNominal() {
        return nominal;
    }
}
