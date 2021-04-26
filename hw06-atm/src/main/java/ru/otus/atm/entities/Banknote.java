package ru.otus.atm.entities;

public class Banknote implements BanknoteInterface {
    private final int nominal;

    public Banknote(int nominal) {
        this.nominal = nominal;
    }

    @Override
    public int getNominal() {
        return nominal;
    }
}
