package ru.otus;

import java.util.ArrayList;
import java.util.Collections;

public class App1 {
    public static void main(String[] args) {

        DIYarrayList<Integer> myArrayList = new DIYarrayList<>();

        System.out.println("addAll DIYarrayList");
        ArrayList collection1 = new ArrayList<Integer>();
        for(int i = 100; i >= 80; i--) {
            collection1.add(i);
        }
        // проверим мою реализацию addAll(Collection<? super T> c, T... elements) на дженериках
        myArrayList.addAll(collection1, 22, 33, 44);

        // проверим стандартную реализацию
        Collections.addAll(myArrayList, 55, 66, 77);

        for (int i = 0; i < myArrayList.size(); i++) {
            System.out.println(myArrayList.get(i));
        }
        System.out.println("");


        System.out.println("copy DIYarrayList");
        DIYarrayList<Integer> myArrayList1 = new DIYarrayList<>(myArrayList.size());

        Collections.copy(myArrayList1, myArrayList);
        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.println(myArrayList1.get(i));
        }
        System.out.println("");



        System.out.println("sort DIYarrayList");
        Collections.sort(myArrayList1, new DIYarrayComparator<Integer>());
        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.println(myArrayList1.get(i));
        }
    }
}
