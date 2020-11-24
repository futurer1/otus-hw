package ru.otus.hw3;

import ru.otus.hw3.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Object;

public class TestService {

    // массивы для сохранения названий методов
    private final ArrayList<String> beforeMethods = new ArrayList<>();
    private final ArrayList<String> testMethods = new ArrayList<>();
    private final ArrayList<String> afterMethods = new ArrayList<>();

    private int success = 0;
    private int fail = 0;

    public void execute (Class<?> testClass) throws Exception
    {
        System.out.println("Test class name: " + testClass.getSimpleName());
        readClass(testClass);
        runMethods(testClass);
    }

    private void saveTestResult(Integer result)
    {
        success += result == 1 ? 1 : 0;
        fail += result == 0 ? 1 : 0;
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
                this.beforeMethods.add(methodsPublic[i].getName());
                System.out.println(methodsPublic[i].getName() + " - Before");
            }

            if (methodsPublic[i].isAnnotationPresent(Test.class)) {
                this.testMethods.add(methodsPublic[i].getName());
                System.out.println(methodsPublic[i].getName() + " - Test");
            }

            if (methodsPublic[i].isAnnotationPresent(After.class)) {
                this.afterMethods.add(methodsPublic[i].getName());
                System.out.println(methodsPublic[i].getName() + " - After");
            }
        }
    }

    private void runMethods(Class<?> testClass) throws Exception
    {
        Constructor<?> constructor = testClass.getConstructor();

        Integer testNumber = 0;

        // выполняем в нужном порядке методы
        for (String testMethod : testMethods) {
            // новый экземпляр класса
            Object clazz = constructor.newInstance();
            System.out.println(" ");
            System.out.println("Test " + ++testNumber + ". Object hash = " + clazz.hashCode());
            Integer result = -1;

            for (String beforeMethod : beforeMethods) {
                System.out.println("Выполняем: " + beforeMethod);
                try {
                    callMethod(clazz, beforeMethod);
                    result = result != 0 ? 1 : 0;
                } catch (Exception e) {
                    result = 0;
                    System.out.println("Исключение в методе: " + beforeMethod);
                }
            }


            System.out.println("Выполняем: " + testMethod);
            try {
                callMethod(clazz, testMethod);
                result = result != 0 ? 1 : 0;
            } catch (Exception e) {
                result = 0;
                System.out.println("Исключение в методе: " + testMethod);
            }


            for (String afterMethod : afterMethods) {
                System.out.println("Выполняем: " + afterMethod);

                try {
                    callMethod(clazz, afterMethod);
                    result = result != 0 ? 1 : 0;
                } catch (Exception e) {
                    result = 0;
                    System.out.println("Исключение в методе: " + afterMethod);
                }
            }

            var mainResult = callMethod(clazz, "getResult");
            System.out.println("Результат: " + mainResult);

            saveTestResult(result);
        }
    }

    public void printReport()
    {
        System.out.println(" ");
        System.out.println("Успешно: " + success);
        System.out.println("Неуспешно: " + fail);
        System.out.println("Всего: " + (success + fail));
    }

    public static Object callMethod(Object object, String name, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(name, toClasses(args));
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
