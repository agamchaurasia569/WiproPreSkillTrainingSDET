package com.example;

/**
 * Enum representing supported browsers for testing
 */
public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    SAFARI("safari");

    private final String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
