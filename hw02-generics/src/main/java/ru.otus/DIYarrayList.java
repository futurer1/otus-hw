package ru.otus;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private static final int BUCKET = 10;
    private int size;
    private T[] myArray;

    public DIYarrayList() {
    }

    public DIYarrayList(int newSize) {
        myArray = (T[]) new Object[newSize];
        size = newSize;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        boostMyArray();
        myArray[size] = t;
        size++;
        return true;
    }

    private void boostMyArray() {
        if (myArray == null || myArray.length == 0) {
            myArray = (T[]) new Object[BUCKET];
            return;
        }
        if (size == myArray.length) {
            T[] newArray = (T[]) new Object[myArray.length + BUCKET];
            for (int i = 0; i < myArray.length; i++) {
                newArray[i] = myArray[i];
            }
            myArray = newArray;
        }
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return myArray;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Свой метод addAll ради эксперимента
     */
    public boolean addAll(Collection<? super T> c, T... elements) {
        if (c == null || c.size() == 0) {
            return false;
        }

        T[] cArray = (T[]) c.toArray();
        T[] newArray = (T[]) new Object[c.size() + elements.length + size];
        for (int i = 0; i < size; i++) {
            newArray[i] = myArray[i];
        }
        for (int i = 0; i < c.size(); i++) {
            newArray[i + size] = cArray[i];
        }
        for (int i = 0; i < elements.length; i++) {
            newArray[i + size + c.size()] = elements[i];
        }
        size = size + c.size() + elements.length;
        myArray = newArray;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        if (index < size) {
            return myArray[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListIterator(0);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class DIYListIterator implements ListIterator<T> {
        private int cursor;

        public DIYListIterator(int cursor) {
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            if (cursor < myArray.length) {
                T next = myArray[cursor];
                cursor++;
                return next;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {
            if (cursor - 1 < 0) {
                throw new IllegalStateException();
            }
            myArray[cursor - 1] = t;
        }

        @Override
        public void add(T t) {

        }
    }
}
