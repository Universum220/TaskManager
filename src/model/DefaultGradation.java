package model;

public enum DefaultGradation {

    MIN("Min", 1),
    MEDIUM_DOWN("Medium down", 2),
    MEDIUM("Medium", 3),
    MEDIUM_UP("Medium up", 4),
    MAX("Max", 5);

    private final String title;
    private int val;

    DefaultGradation(String title, int val) {
        this.title = title;
        this.val = val;
    }

}
