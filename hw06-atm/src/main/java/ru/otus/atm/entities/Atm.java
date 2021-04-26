package ru.otus.atm.entities;

/**
 * Банкомат с обработчиком
 */

public class Atm {
    private CompartmentCells allCells = new CompartmentCells();

    /**
     * @param cells создать ячейки для денег
     */
    public void createCells(BanknoteCell... cells) {
        this.allCells.addCells(cells);
    }

    /**
     * @param banknote положить банкноту в банкомат
     */
    public void deposit(BanknoteInterface banknote) {
        this.allCells.depositUnitIntoCell(banknote);
    }

    /**
     * @param processor стратегия выдачи денег
     * @param val сумма к выдаче
     */
    public void withdraw(AtmWithdrawStrategy processor, int val) {
        CompartmentCells updatedCells = processor.withdraw(this.allCells, val);
        updatedCells.setBalanceChanged();
        this.setAllCells(updatedCells);
    }

    /**
     * @return получить баланс банкомата
     */
    public int getBalance() {
        return this.allCells.getBalance();
    }

    /**
     * @return подробная детализация по балансу банкомата
     */
    public String getDetailBalance() {
        return this.allCells.getDetailedBalance();
    }
    
    private void setAllCells(CompartmentCells allCells) {
        this.allCells = allCells;
    }
}
