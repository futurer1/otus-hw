package ru.otus.atm.entities;

import java.util.*;

/**
 * Контейнер с ячейками банкомата
 */
public class CompartmentCells extends AbstractCell {
    protected List<BanknoteCell> allCells = new ArrayList<>();
    private int balance;

    /**
     * менялся ли баланс с момента последнего получения баланса
     */
    private boolean balanceChanged;

    public CompartmentCells(BanknoteCell... cells) {
        super(0, 0);
        addCells(cells);
        this.balanceChanged = true;
    }

    public void addCells(BanknoteCell cell) {
        allCells.add(cell);
    }

    public void addCells(BanknoteCell... cells) {
        allCells.addAll(Arrays.asList(cells));
        this.sortCells(false);
    }

    public List<BanknoteCell> getAllCells() {
        return this.allCells;
    }

    /**
     * Пополнение баланса одной банкнотой
     * @param banknote
     */
    public void depositUnitIntoCell(BanknoteInterface banknote) {
        if (!this.isSupportedNominal(banknote)) {
            throw new IllegalArgumentException(
                    String.format("No such cell nominal %d into ATM. Can`t deposit this nominal banknote.",
                            banknote.getNominal())
            );
        }

        for (BanknoteCell cell : allCells) {
            if (cell.getNominal() == banknote.getNominal()) {
                cell.depositUnits(1);
                this.balanceChanged = true;
                return;
            }
        }
    }

    private boolean isSupportedNominal(BanknoteInterface banknote) {
        Boolean find = false;
        for (BanknoteCell cell : allCells) {
            if (cell.getNominal() == banknote.getNominal()) {
                find = true;
            }
        }
        return find;
    }

    /**
     * Подсчет баланса
     */
    private void calculateBalance() {
        int newBalance = 0;
        for (BanknoteCell cell : allCells) {
            newBalance += cell.getSumma();
        }
        this.balance = newBalance;
    }

    /**
     * @return получение суммы баланса
     */
    public int getBalance() {
        if (this.balanceChanged) {
            this.calculateBalance();
            this.balanceChanged = false;
        }
        return this.balance;
    }

    /**
     * @return получение подробной детализации по балансу
     */
    public String getDetailedBalance() {
        String str = "";
        this.sortCells(true);
        for (BanknoteCell cell : allCells) {
            str += "nominal " + cell.cellNominal + ": " + cell.getSumma() + "\n";
        }
        return str;
    }

    /**
     * @param amount
     * @return проверяет имеется ли указанная сумма в купюрах
     */
    public boolean doesContainsAmountByBanknotes(int amount) {
        int remainder = amount;
        this.sortCells(false);
        for (BanknoteCell cell : allCells) {
            if (remainder >= cell.getNominal()) {
                int sum = remainder/cell.getNominal() * cell.getNominal();
                if (sum > cell.getSumma()) { // максимально можно снять из ячейки всё
                    sum = cell.getSumma();
                }
                remainder -= sum;
            }
        }
        return remainder == 0;
    }

    /**
     * @param asc сортировка ячеек банкомата по возрастанию/убыванию номинала
     */
    public void sortCells(boolean asc) {
        if (asc) {
            Collections.sort(allCells, (o1, o2) -> o1.getNominal() - o2.getNominal());
        } else {
            Collections.sort(allCells, (o1, o2) -> o2.getNominal() - o1.getNominal());
        }
    }

    public void setBalanceChanged()
    {
        this.balanceChanged = true;
    }
}
