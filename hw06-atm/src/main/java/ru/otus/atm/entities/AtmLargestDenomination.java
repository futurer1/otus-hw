package ru.otus.atm.entities;

import java.util.Collections;

/**
 * Конкретная стратегия обработчика банкомата.
 * Реализует выдачу заказанной суммы наиболее крупными из имеющихся в ячейках купюр.
 */
public class AtmLargestDenomination implements AtmWithdrawStrategy {

    @Override
    public CompartmentCells withdraw(CompartmentCells cells, int val) {
        this.checkBalance(cells, val);
        cells.sortCells(false);

        int remainder = val;
        String report = "";

        for (BanknoteCell cell : cells.getAllCells()) {
            if (remainder >= cell.getNominal()) {
                int sum = remainder/cell.getNominal() * cell.getNominal();
                if (sum > cell.getSumma()) { // максимально можно снять из ячейки всё
                    sum = cell.getSumma();
                }
                report += "номинал " + cell.getNominal() + " в кол.-ве " + (sum/cell.getNominal()) + " шт.\n";
                remainder -= sum;
                cell.withdrawUnits(sum/cell.getNominal());
            }
        }
        this.issuingMoneyReport(report);
        return cells;
    }

    private void issuingMoneyReport(String report) {
        System.out.printf("Выданы купюры: \n" + report);
    }

    private void checkBalance(CompartmentCells cells, int val) throws ArithmeticException {
        if (cells.getBalance() < val) {
            throw new ArithmeticException(
                String.format("No such money in ATM! Able to withdraw amounts to %d.", cells.getBalance())
            );
        }

        if (!cells.doesContainsAmountByBanknotes(val)) {
            throw new ArithmeticException("No such banknotes in ATM!");
        }
    }
}
