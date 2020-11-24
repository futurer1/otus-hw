package ru.otus.hw3;

public class App {
    public static void main(String[] args) throws Exception {
        Class<?> classResearched = ResearchedClass.class;
        Runner.main(classResearched.getSimpleName());
    }
}
