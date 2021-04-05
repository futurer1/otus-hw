package ru.otus.autologging;

import ru.otus.autologging.annotations.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class DemoHandler implements InvocationHandler {

    private final TestLoggingInterface testClass;

    private final Map<String, Method> methodsLog = new HashMap<>();

    public DemoHandler(TestLoggingInterface testClass) {
        this.testClass = testClass;

        for (Method testClassMethod : testClass.getClass().getDeclaredMethods()) {
            if (testClassMethod.getAnnotation(Log.class) != null) {
                // метод имеет аннотацию Log, добавим его в список логирования
                methodsLog.put(testClassMethod.getName() + getMethodParametersString(testClassMethod), testClassMethod);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method foundMethod = methodsLog.get(method.getName() + getMethodParametersString(method));

        if (foundMethod != null) {
            echoLog(method, args);
        }
        return method.invoke(testClass, args);
    }

    private String getMethodParametersString(Method method) {
        Parameter[] parameters = method.getParameters();
        String str = "";

        for (Parameter param : parameters) {
            str += param.getName() + param.getType().toString();
        }
        return str;
    }

    private void echoLog(Method method, Object[] args)
    {
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
}
