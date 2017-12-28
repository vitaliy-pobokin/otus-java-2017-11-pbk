package org.examples.pbk.otus.l61homework;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ATM atm = getDefaultATM();
        Banknote b5000 = new Banknote(BanknoteType.FIVE_THOUSAND, "AA 1111111");
        Banknote b1000 = new Banknote(BanknoteType.ONE_THOUSAND, "AA 2222222");
        Banknote b500_1 = new Banknote(BanknoteType.FIVE_HUNDRED, "AA 3333333");
        Banknote b500_2 = new Banknote(BanknoteType.FIVE_HUNDRED, "AA 4444444");
        Banknote b100 = new Banknote(BanknoteType.ONE_HUNDRED, "AA 5555555");
        Banknote b50_1 = new Banknote(BanknoteType.FIFTY, "AA 6666666");
        Banknote b50_2 = new Banknote(BanknoteType.FIFTY, "AA 7777777");

        atm.deposit(b50_1, b50_2, b100, b500_1, b500_2, b5000, b1000);
        System.out.println("ATM balance: " + atm.getAvailableBalance());
        printBanknoteList(atm.withdraw(6100));

        Thread.sleep(50);

        atm.withdraw(700);
    }

    private static ATM getDefaultATM() {
        BanknoteCell c5000 = new BanknoteCell(BanknoteType.FIVE_THOUSAND);
        BanknoteCell c1000 = new BanknoteCell(BanknoteType.ONE_THOUSAND);
        BanknoteCell c500 = new BanknoteCell(BanknoteType.FIVE_HUNDRED);
        BanknoteCell c100 = new BanknoteCell(BanknoteType.ONE_HUNDRED);
        BanknoteCell c50 = new BanknoteCell(BanknoteType.FIFTY);
        return new ATM(c5000, c1000, c500, c100, c50);
    }

    private static void printBanknoteList(List<Banknote> list) {
        System.out.println("Returned banknotes:");
        for(Banknote banknote : list) {
            System.out.println(banknote);
        }
    }
}
