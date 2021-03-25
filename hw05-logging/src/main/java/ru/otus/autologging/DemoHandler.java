package ru.otus.autologging;

import ru.otus.autologging.annotations.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DemoHandler implements InvocationHandler {

    private final Object testClass;

    private final Set<Method> methodsLog = new HashSet<>();

    public DemoHandler(Object testClass) {
        this.testClass = testClass;
        for (Method testClassMethod : testClass.getClass().getDeclaredMethods()) {
            if (testClassMethod.getAnnotation(Log.class) != null) {
                // метод имеет аннотацию Log, добавим его в список логирования
                methodsLog.add(testClassMethod);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodsLog.contains(method)) {
            ArrayList argsValues = new ArrayList<String>();
            for (Object arg : args) {
                String str =  String.valueOf(arg);
                argsValues.add(str);
            }

            System.out.println(
                String.format("executed method: %s, params: %s",
                    method.getName(),
                    String.join(", ", argsValues)
                )
            );
        }
        return method.invoke(testClass, args);
    }
}
