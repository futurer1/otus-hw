package ru.otus.atm.entities;

/**
 * Общий интерфейс стратегий выдачи денег банкоматом
 */
public interface AtmWithdrawStrategy {

    /**
     * @param val выдать нужную сумму юнитами из ячеек
     */
    public CompartmentCells withdraw(CompartmentCells cells, int val);
}
