package org.examples.pbk.otus.l71homework;

import java.util.*;

public class AtmDepartment {
    private Set<ATM> atmSet;
    private Map<ATM, List<AtmState>> atmStates;
    private Map<ATM, Integer> atmBalance;

    public AtmDepartment() {
        this.atmSet = new HashSet<>();
        this.atmStates = new HashMap<>();
        this.atmBalance = new HashMap<>();
    }

    public void registerATM(ATM atm) {
        AtmState initialState = atm.saveState();
        List<AtmState> states = new ArrayList<>();
        states.add(initialState);
        atmSet.add(atm);
        atmStates.put(atm, states);
        atmBalance.put(atm, atm.getAvailableBalance());
    }

    public void saveState(ATM atm) {
        atmStates.get(atm).add(atm.saveState());
    }

    public void restoreAllATMs() {
        atmSet.forEach(atm -> restoreATMInitialState(atm));
    }

    public void restoreATMInitialState(ATM atm) {
        AtmState state = atmStates.get(atm).get(0);
        atm.restoreState(state);
    }

    public void updateBalances() {
        for (ATM atm : atmBalance.keySet()) {
            atmBalance.put(atm, atm.getAvailableBalance());
        }
    }

    public void printBalances() {
        atmBalance.forEach((atm, balance) -> System.out.println(atm + " balance = " + balance));
    }
}
