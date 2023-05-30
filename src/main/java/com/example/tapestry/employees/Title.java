package com.example.tapestry.employees;

public enum Title {
    ING("ING"),
    MUDR("MUDR"),
    JUDR("JUDR"),
    BC("BC"),
    WTF("WTF");

    private String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
