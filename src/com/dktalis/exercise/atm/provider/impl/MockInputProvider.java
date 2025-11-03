package com.dktalis.exercise.atm.provider.impl;

import com.dktalis.exercise.atm.provider.InputProvider;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class MockInputProvider implements InputProvider {

    LinkedList<String> inputMap = new LinkedList<>();
    public MockInputProvider(String ...inputs){
        inputMap.addAll(Arrays.asList(inputs));
    }

    @Override
    public String readInput() {
        return inputMap.poll();
    }

    @Override
    public void close() {
    }
}
