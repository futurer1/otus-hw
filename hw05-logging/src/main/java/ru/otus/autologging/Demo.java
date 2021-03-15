package ru.otus.autologging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class Demo {

    public static void main(String[] args) {

        TestLoggingInterface testClass = createMyClass();

        int res1 = testClass.calculation(6);
        System.out.println("Result: " + res1);

        int res2 = testClass.calculation(5, 4);
        System.out.println("Result: " + res2);

        String res3 = testClass.calculation(5, 4, "kmh");
        System.out.println("Result: " + res3);

        int res4 = testClass.calculation(5, 4, 1); // not Log
        System.out.println("Result: " + res4);
    }

    private static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Demo.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler
        );
    }
}
