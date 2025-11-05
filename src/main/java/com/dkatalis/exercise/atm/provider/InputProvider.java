package com.dkatalis.exercise.atm.provider;

public interface InputProvider {
    public String readInput();
    public boolean isOpen();
    public void close();
}
