package org.examples.pbk.otus.l61homework;

import org.junit.Test;

import static org.junit.Assert.*;

public class BanknoteCellTests {
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWrongInitialQuantity() {
        new BanknoteCell(BanknoteType.FIFTY, 100, 10);
    }

    @Test
    public void testGetAvailableQuantity() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY, 10, 100);
        int availableQuantity = banknoteCell.getAvailableQuantity();
        assertEquals(availableQuantity, 10);
    }

    @Test
    public void testAddBanknote() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY, 99, 100);
        int quantityBeforeAdd = banknoteCell.getAvailableQuantity();
        banknoteCell.add(new Banknote(BanknoteType.FIFTY, "AA 1234567"));
        int quantityAfterAdd = banknoteCell.getAvailableQuantity();
        assertEquals(quantityBeforeAdd + 1, quantityAfterAdd);
    }

    @Test(expected = RuntimeException.class)
    public void testAddBanknotesExceedsCellSizeCantBeAdded() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY, 100, 100);
        banknoteCell.add(new Banknote(BanknoteType.FIFTY, "AA 1234567"));
    }

    @Test
    public void testGetBanknote() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY, 1, 100);
        int quantityBeforeGet = banknoteCell.getAvailableQuantity();
        banknoteCell.get();
        int quantityAfterGet = banknoteCell.getAvailableQuantity();
        assertEquals(quantityBeforeGet - 1, quantityAfterGet);
    }

    @Test(expected = RuntimeException.class)
    public void testGetBanknoteExceedsAvailableQuantity() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY, 0, 100);
        banknoteCell.get();
    }

    @Test
    public void testGetBanknoteType() {
        BanknoteCell banknoteCell = new BanknoteCell(BanknoteType.FIFTY);
        BanknoteType banknoteType = banknoteCell.getBanknoteType();
        assertTrue(banknoteType.equals(BanknoteType.FIFTY));
    }
}
