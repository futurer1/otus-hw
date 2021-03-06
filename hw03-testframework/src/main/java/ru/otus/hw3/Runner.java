package ru.otus.hw3;

import java.lang.Class;

/**
 * Запускалка тестов. По условиям задачи должна принимать имя класса с тестами, в котором есть аннотации.
 */
public class Runner {
    public static void run(String nameClass) throws Exception {
        Class<?> classResearched = Class.forName(nameClass);
        TestService service = new TestService();
        service.execute(classResearched);
        service.printReport();
    }
}
