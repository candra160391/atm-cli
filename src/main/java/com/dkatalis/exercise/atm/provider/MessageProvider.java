package com.dkatalis.exercise.atm.provider;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public interface MessageProvider {
    public String get(String key, Object... args);
}
