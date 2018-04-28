package org.examples.pbk.otus.l71homework;

import java.util.*;

public class AtmState {

    private List<BanknoteCellState> banknoteCellStates;

    public AtmState(Collection<BanknoteCell> stateToSave) {
        banknoteCellStates = new ArrayList<>(stateToSave.size());
        stateToSave.forEach(cell -> banknoteCellStates.add(
                new BanknoteCellState(cell.getBanknoteType(), cell.getAvailableQuantity(), cell.getCellSize())));
    }

    public List<BanknoteCellState> getState() {
        return banknoteCellStates;
    }

    class BanknoteCellState {
        BanknoteType type;
        int banknoteQuantity;
        int cellSize;

        BanknoteCellState(BanknoteType type, int banknoteQuantity, int cellSize) {
            this.type = type;
            this.banknoteQuantity = banknoteQuantity;
            this.cellSize = cellSize;
        }
    }
}
