package com.opet.console_app.model;

public class Console {
    private long id;
    private String name;
    private int year;
    private double price;
    private boolean active;
    private int amountGames;

    public Console() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAmountGames() {
        return amountGames;
    }

    public void setAmountGames(int amountGames) {
        this.amountGames = amountGames;
    }
}
