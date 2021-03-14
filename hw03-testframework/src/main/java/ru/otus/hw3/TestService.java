package ru.otus.hw3;

import ru.otus.hw3.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Object;
import java.util.List;

public class TestService {

    // массивы для сохранения названий методов
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    private int countSuccess = 0;
    private int countFail = 0;

    private final int SUCCESS = 1;
    private final int FAIL = 0;
    private final int UNKNOWN_RESULT = -1;

    private int getSuccessIfNotTotalFail(int result) {
        return (result != FAIL) ? SUCCESS : FAIL;
    }

    public void execute (Class<?> testClass)
    {
        System.out.println("Test class name: " + testClass.getSimpleName());
        readClass(testClass);
        runAllMethods(testClass);
    }

    private void saveTestResult(int result)
    {
        countSuccess += result == SUCCESS ? 1 : 0;
        countFail += result == FAIL ? 1 : 0;
    }

    /**
     * Разбираем методы в классе
     *
     * @param testClass
     */
    private void readClass(Class<?> testClass)
    {
        Method[] methodsPublic = testClass.getDeclaredMethods();
        System.out.println(" ");
        System.out.println("Analyze public methods:");

        // проходим по методам
        for (int i = 0; i < methodsPublic.length; i++) {
            Annotation[] annotations = methodsPublic[i].getDeclaredAnnotations();

            //проходим по аннотациям
            //Arrays.stream(annotations).forEach(annotation -> System.out.println(annotation.toString()));

            if (methodsPublic[i].isAnnotationPresent(Before.class)) {
                this.beforeMethods.add(methodsPublic[i]);
                System.out.println(methodsPublic[i].getName() + " - Before");
            }

            if (methodsPublic[i].isAnnotationPresent(Test.class)) {
                this.testMethods.add(methodsPublic[i]);
                System.out.println(methodsPublic[i].getName() + " - Test");
            }

            if (methodsPublic[i].isAnnotationPresent(After.class)) {
                this.afterMethods.add(methodsPublic[i]);
                System.out.println(methodsPublic[i].getName() + " - After");
            }
        }
    }

    private void runAllMethods(Class<?> testClass)
    {
        try {
            Constructor<?> constructor = testClass.getConstructor();
            int testNumber = 0;

            // выполняем в нужном порядке методы
            for (Method testMethod : testMethods) {
                // новый экземпляр класса
                Object clazz = constructor.newInstance();
                System.out.println(" ");
                System.out.println("Test " + ++testNumber + ". Object hash = " + clazz.hashCode());
                int testResult = UNKNOWN_RESULT;

                testResult = runMethods(clazz, beforeMethods, testResult);
                if (testResult == SUCCESS || testResult == UNKNOWN_RESULT) {
                    List<Method> oneTestMethod = new ArrayList<>();
                    oneTestMethod.add(testMethod);
                    testResult = runMethods(clazz, oneTestMethod, testResult);
                }
                testResult = runMethods(clazz, afterMethods, testResult);
                saveTestResult(testResult);
            }
        } catch (Exception e) {
            System.out.println("Не удалось запустить тестируемые методы класса: " + e.getMessage());
        }
    }

    private int runMethods(Object clazz, List<Method> methods, int prevResult)
    {
        int result = SUCCESS;
        for (Method method : methods) {
            System.out.println("Выполняем: " + method.getName());
            try {
                callMethod(clazz, method);
                result = getSuccessIfNotTotalFail(prevResult);
            } catch (Exception e) {
                result = FAIL;
                System.out.println("Исключение в методе: " + method.getName());
            }
        }
        return result;
    }

    public void printReport()
    {
        System.out.println(" ");
        System.out.println("Успешно: " + countSuccess);
        System.out.println("Неуспешно: " + countFail);
        System.out.println("Всего: " + (countSuccess + countFail));
    }

    public static Object callMethod(Object object, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
