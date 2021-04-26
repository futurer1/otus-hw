package ru.otus.atm.entities;

/**
 * Класс ячейки банкомата
 */
public class BanknoteCell extends AbstractCell {

    public BanknoteCell(int cellNominal, int cellCapacity) {
        super(cellNominal, cellCapacity);
    }

    public int getSumma() {
        return this.cellNominal * this.countUnits;
    }

    public int getNominal() {
        return this.cellNominal;
    }
}
