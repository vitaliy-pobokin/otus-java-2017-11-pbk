package org.examples.pbk.otus.l71homework;

public class Main {
    public static void main(String[] args) {
        BanknoteCell c1000 = new BanknoteCell(BanknoteType.ONE_THOUSAND, 5, 100);
        BanknoteCell c500 = new BanknoteCell(BanknoteType.FIVE_HUNDRED, 3, 100);

        AtmDepartment department = new AtmDepartment();
        ATM atm1 = new ATM(c1000, c500);

        System.out.println("ATM_1 balance: " + atm1.getAvailableBalance());
        department.registerATM(atm1);

        c1000 = new BanknoteCell(BanknoteType.ONE_THOUSAND, 4, 100);
        c500 = new BanknoteCell(BanknoteType.FIVE_HUNDRED, 7, 100);
        ATM atm2 = new ATM(c1000, c500);

        System.out.println("ATM_2 balance: " + atm2.getAvailableBalance());
        department.registerATM(atm2);

        department.printBalances();

        atm1.withdraw(6000);
        atm2.withdraw(500);

        department.updateBalances();
        department.printBalances();

        department.restoreAllATMs();
        department.updateBalances();
        department.printBalances();
    }
}
