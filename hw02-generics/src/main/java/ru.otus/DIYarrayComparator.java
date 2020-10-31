package ru.otus;

import java.util.Comparator;

public class DIYarrayComparator<T> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        // из-за того, что у нас BUCKET = 10 мы выигрываем в производительности при добавлении элементов на расширении массива,
        // но если заполнены не все ячейки массива, то в компаратор попадают элементы со значением null и это приводит к падению
        // из-за невозможности сравнить разные типы
        // Таким подходом мы выводим из сравнения null с переменными другого типа.

        return (o1 == null || o2 == null) ? 0 : ((Comparable<? super T>) o1).compareTo(o2);
    }
}
