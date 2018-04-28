package org.examples.pbk.otus.l71homework;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ATMTests {
    private ATM atm;
    private Banknote b5000 = new Banknote(BanknoteType.FIVE_THOUSAND, "AA 1111111");
    private Banknote b1000 = new Banknote(BanknoteType.ONE_THOUSAND, "AA 3333333");
    private Banknote b500_1 = new Banknote(BanknoteType.FIVE_HUNDRED, "AA 5555555");
    private Banknote b500_2 = new Banknote(BanknoteType.FIVE_HUNDRED, "AA 6666666");
    private Banknote b100 = new Banknote(BanknoteType.ONE_HUNDRED, "AA 7777777");
    private Banknote b50_1 = new Banknote(BanknoteType.FIFTY, "AA 9999999");
    private Banknote b50_2 = new Banknote(BanknoteType.FIFTY, "AA 0000000");
    private Banknote b10 = new Banknote(BanknoteType.TEN, "AA 1010101");

    @Before
    public void initATM() {
        BanknoteCell c5000 = new BanknoteCell(BanknoteType.FIVE_THOUSAND);
        BanknoteCell c1000 = new BanknoteCell(BanknoteType.ONE_THOUSAND);
        BanknoteCell c500 = new BanknoteCell(BanknoteType.FIVE_HUNDRED);
        BanknoteCell c100 = new BanknoteCell(BanknoteType.ONE_HUNDRED);
        BanknoteCell c50 = new BanknoteCell(BanknoteType.FIFTY);
        this.atm = new ATM(c5000, c1000, c500, c100, c50);
    }

    @Test
    public void testATMdeposit() {
        int availableBalanceBeforeAdd = atm.getAvailableBalance();
        atm.deposit(b5000, b1000);
        int addedMoney = b5000.getType().getNominal() + b1000.getType().getNominal();
        int availableBalanceAfterAdd = atm.getAvailableBalance();
        assertEquals(availableBalanceBeforeAdd + addedMoney, availableBalanceAfterAdd);
    }

    @Test
    public void testATMdepositNotMaintainableBanknote() {
        int availableBalanceBeforeAdd = atm.getAvailableBalance();
        atm.deposit(b10);
        int availableBalanceAfterAdd = atm.getAvailableBalance();
        assertEquals(availableBalanceBeforeAdd, availableBalanceAfterAdd);
    }

    @Test
    public void testATMdepositEmptyArray() {
        int availableBalanceBeforeAdd = atm.getAvailableBalance();
        atm.deposit();
        int availableBalanceAfterAdd = atm.getAvailableBalance();
        assertEquals(availableBalanceBeforeAdd, availableBalanceAfterAdd);
    }

    @Test
    public void testATMwithdrawZero() {
        int availableBalanceBeforeAdd = atm.getAvailableBalance();
        List<Banknote> banknotes = atm.withdraw(0);
        int availableBalanceAfterAdd = atm.getAvailableBalance();
        assertEquals(availableBalanceBeforeAdd, availableBalanceAfterAdd);
        assertTrue(banknotes.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testATMwithdrawNotEnoughBalance() {
        atm.deposit(b5000);
        atm.withdraw(10000);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testATMwithdrawSuitableBanknoteAbsence() {
        atm.deposit(b5000);
        atm.withdraw(1000);
    }

    @Test
    public void testATMwithdrawUsingLargestBanknotes() {
        atm.deposit(b50_2, b50_1, b100, b500_2, b500_1, b1000, b5000);
        List<Banknote> banknotes = atm.withdraw(6600);
        assertEquals(4, banknotes.size());
        List<BanknoteType> banknoteTypes = toBanknoteTypeList(banknotes);
        assertTrue(banknoteTypes.containsAll(Arrays.asList(BanknoteType.FIVE_THOUSAND, BanknoteType.ONE_THOUSAND, BanknoteType.FIVE_HUNDRED, BanknoteType.ONE_HUNDRED)));
    }

    private List<BanknoteType> toBanknoteTypeList(List<Banknote> banknotes) {
        List<BanknoteType> list = new ArrayList<>();
        for (Banknote b : banknotes) {
            list.add(b.getType());
        }
        return list;
    }

}
