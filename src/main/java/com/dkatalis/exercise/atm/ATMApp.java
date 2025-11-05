package com.dkatalis.exercise.atm;
import com.dkatalis.exercise.atm.manager.ATMManager;

public class ATMApp {
    public static void main(String[] args) {
        ATMManager atmManager = new ATMManager();
        atmManager.runInteractive();
    }
}