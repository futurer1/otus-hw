package ru.otus.atm.entities;

/**
 * Абстракция ячейки. Это может быть ячейка банкомата, а может быть ячейка аппарата по выдаче напитков и т.д.
 */
abstract public class AbstractCell implements CellInterface {
    private final int cellCapacity; // вместимость ячейки

    protected int cellNominal; // номинал юнита в ячейке (это может быть номинал купюры или id вида напитков)
    protected int countUnits; // кол.-во экземпляров юнита в ячейке

    public AbstractCell(int cellNominal, int cellCapacity) {
        this.cellNominal = cellNominal;
        this.cellCapacity = cellCapacity;
    }

    @Override
    public void depositUnits(int count) throws ArithmeticException {
        if (this.countUnits + count > this.cellCapacity) {
            throw new ArithmeticException(
                    String.format("%d is too much units for deposit to cell nominal %d ! Capacity: %d. Free cell space: %d.",
                            count,
                            this.cellNominal,
                            this.cellCapacity,
                            this.cellCapacity - this.countUnits)
            );
        }
        this.countUnits += count;
    }

    @Override
    public void withdrawUnits(int count) {
        if (this.countUnits < count) {
            throw new ArithmeticException(
                    String.format("Not enough quantity units in cell nominal %d for withdraw %d. Cell contains %d units.",
                            this.cellNominal,
                            count,
                            this.countUnits)
            );
        }
        this.countUnits -= count;
    }
}
