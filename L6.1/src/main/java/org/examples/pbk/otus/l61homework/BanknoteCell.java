package org.examples.pbk.otus.l61homework;

import java.util.ArrayDeque;
import java.util.Queue;

class BanknoteCell {
    private final BanknoteType banknoteType;
    private Queue<Banknote> banknotes;
    private final int cellSize;

    BanknoteCell(BanknoteType banknoteType) {
        this(banknoteType, 0, 100);
    }

    BanknoteCell(BanknoteType banknoteType, int initialQuantity, int cellSize) {
        if (initialQuantity > cellSize) {
            throw new IllegalArgumentException("initial quantity cannot be larger than cell size");
        }
        this.banknoteType = banknoteType;
        this.cellSize = cellSize;
        fillCell(initialQuantity);
    }

    private void fillCell(int initialQuantity) {
        this.banknotes = new ArrayDeque<>(initialQuantity);
        for (int i = 0; i < initialQuantity; i++) {
            banknotes.add(new Banknote(banknoteType, "AA 1234567"));
        }
    }

    int getAvailableQuantity() {
        return banknotes.size();
    }

    boolean isEmpty() {
        return banknotes.size() == 0;
    }

    boolean isFull() {
        return banknotes.size() == cellSize;
    }

    void add(Banknote banknote) {
        if (isFull()) {
            throw new RuntimeException("banknote can't be added - cell is full");
        }
        banknotes.add(banknote);
    }

    Banknote get() {
        if (isEmpty()) {
            throw new RuntimeException("cell is empty");
        }
        return banknotes.remove();
    }

    BanknoteType getBanknoteType() {
        return banknoteType;
    }
}
