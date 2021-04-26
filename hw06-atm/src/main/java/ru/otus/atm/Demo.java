package ru.otus.atm;

import ru.otus.atm.entities.Atm;
import ru.otus.atm.entities.AtmLargestDenomination;
import ru.otus.atm.entities.Banknote;
import ru.otus.atm.entities.BanknoteCell;

public class Demo {

    public static void main(String[] args) {
        System.out.println("Hello ATM!");

        // создаем банкомат с ячейками для банкнот
        Atm atm = new Atm();

        atm.createCells (
            new BanknoteCell(100, 100),
            new BanknoteCell(50, 100),
            new BanknoteCell(200, 100),
            new BanknoteCell(1000, 100),
            new BanknoteCell(500, 100),
            new BanknoteCell(5000, 100),
            new BanknoteCell(2000, 100)
        );

        // добавляем банкноты
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(50));
        atm.deposit(new Banknote(50));
        atm.deposit(new Banknote(500));
        atm.deposit(new Banknote(200));

        // вывести баланс
        System.out.println("balance: " + atm.getBalance() + "\n");

        // вывести подробный баланс
        System.out.println("---\ndetail balance: \n" + atm.getDetailBalance() + "---\n");

        // выдать банкнотами сумму по стратегии "Крупными купюрами"
        atm.withdraw(new AtmLargestDenomination(), 350);

        // вывести баланс
        System.out.println("balance: " + atm.getBalance() + "\n");

        // вывести подробный баланс
        System.out.println("---\ndetail balance: \n" + atm.getDetailBalance() + "---\n");

    }
}