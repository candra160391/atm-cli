package com.dkatalis.exercise.atm.provider.impl;

import com.dkatalis.exercise.atm.provider.MessageProvider;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResourceBundleMessageProvider implements MessageProvider {
    private final ResourceBundle bundle;

    public ResourceBundleMessageProvider() {
        bundle = ResourceBundle.getBundle("messages");
    }

    public String get(String key, Object... args) {
        try {
            String pattern = bundle.getString(key);
            return MessageFormat.format(pattern, args);
        } catch (Exception e) {
            return null;
        }
    }
}
