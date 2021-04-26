package ru.otus.atm.entities;

import java.io.IOException;

public interface CellInterface {

    /**
     * @param count выдать кол.-во юнитов
     * @throws ArithmeticException
     */
    public void withdrawUnits(int count) throws ArithmeticException;

    /**
     * @param count положить кол.-во юнитов
     * @throws ArithmeticException
     */
    public void depositUnits(int count) throws ArithmeticException;

}
