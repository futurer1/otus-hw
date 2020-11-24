package ru.otus.hw3;

import java.lang.Class;

/**
 * Запускалка тестов. По условиям задачи должна принимать имя класса с тестами, в котором есть аннотации.
 */
public class Runner {
    public static void main(String nameClass) throws Exception {
        String namePackage = App.class.getPackage().getName();
        Class<?> classResearched = Class.forName(String.join(".", namePackage, nameClass));
        TestService service = new TestService();
        service.execute(classResearched);
        service.printReport();
    }
}
