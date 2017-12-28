package org.examples.pbk.otus.l61homework;

import java.util.*;

public class ATM {
    private Map<BanknoteType, BanknoteCell> cells;

    public ATM(BanknoteCell... cells) {
        checkCellsNumber(cells);
        initBanknoteCells(cells);
    }

    private void initBanknoteCells(BanknoteCell[] cells) {
        this.cells = new HashMap<>();
        for (BanknoteCell cell : cells) {
            if (this.cells.containsKey(cell.getBanknoteType())) {
                throw new IllegalArgumentException("Cells must be loaded with distinct banknote types");
            }
            this.cells.put(cell.getBanknoteType(), cell);
        }
    }

    private void checkCellsNumber(BanknoteCell[] cells) {
        if (cells.length == 0 || cells.length > 6) {
            throw new IllegalArgumentException("ATM should be loaded at least 1 and at most 6 cells");
        }
    }

    public int getAvailableBalance() {
        int availableBalance = 0;
        for (BanknoteCell cell : cells.values()) {
            availableBalance += cell.getAvailableQuantity() * cell.getBanknoteType().getNominal();
        }
        return availableBalance;
    }

    public List<Banknote> deposit(Banknote... banknotes) {
        return addBanknotes(Arrays.asList(banknotes));
    }

    public List<Banknote> withdraw(int sum) {
        if (isWithdrawableSum(sum)) {
            return getBanknotes(sum);
        }
        throw new IllegalArgumentException("Asked sum can't be withdrawn");
    }

    private Optional<Banknote> addBanknote(Banknote banknote) {
        if (isEligibleBanknote(banknote)) {
            BanknoteCell cell = cells.get(banknote.getType());
            if (cell != null && !cell.isFull()) {
                cell.add(banknote);
                return Optional.empty();
            }
        }
        return Optional.of(banknote);
    }

    private boolean isEligibleBanknote(Banknote banknote) {
        return !isFake(banknote) && cells.containsKey(banknote.getType());
    }

    private boolean isFake(Banknote banknote) {
        return false;
    }

    private List<Banknote> addBanknotes(List<Banknote> banknotes) {
        List<Banknote> returnedBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            Optional<Banknote> o = addBanknote(banknote);
            o.ifPresent(returnedBanknotes::add);
        }
        return returnedBanknotes;
    }

    private List<Banknote> getBanknotes(int sum) {
        List<Banknote> banknotes = new ArrayList<>();
        List<BanknoteCell> cellList = getSortedListOfNonEmptyCells();
        for (BanknoteCell cell : cellList) {
            while (sum >= cell.getBanknoteType().getNominal() && cell.getAvailableQuantity() > 0) {
                Banknote b = cell.get();
                banknotes.add(b);
                sum -= cell.getBanknoteType().getNominal();
            }
        }
        return banknotes;
    }

    private boolean isWithdrawableSum(int sum) {
        if (sum > getAvailableBalance()) return false;
        List<BanknoteCell> list = getSortedListOfNonEmptyCells();
        for (BanknoteCell cell : list) {
            int availableBanknotes = cell.getAvailableQuantity();
            while (sum >= cell.getBanknoteType().getNominal() && availableBanknotes > 0) {
                availableBanknotes--;
                sum -= cell.getBanknoteType().getNominal();
            }
        }
        return sum == 0;
    }

    private List<BanknoteCell> getSortedListOfNonEmptyCells() {
        List<BanknoteCell> list = new ArrayList<>();
        for (BanknoteCell cell : cells.values()) {
            if (!cell.isEmpty()) {
                list.add(cell);
            }
        }
        list.sort(Comparator.comparing((BanknoteCell c) -> c.getBanknoteType().getNominal()).reversed());
        return list;
    }
}
