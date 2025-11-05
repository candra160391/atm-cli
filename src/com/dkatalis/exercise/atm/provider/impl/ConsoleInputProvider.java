package com.dkatalis.exercise.atm.provider.impl;

import com.dkatalis.exercise.atm.provider.InputProvider;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
    Scanner in = new Scanner(System.in);

    @Override
    public String readInput() {
        return in.nextLine();
    }

    @Override
    public void close() {
        in.close();
    }
}
