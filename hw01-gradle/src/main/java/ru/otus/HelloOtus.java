package ru.otus;

import java.util.Arrays;
import com.google.common.base.Joiner;

public class HelloOtus {
    public static String str1;
    public static String str2;

    public static void main(String... args) {
        str1 = "Hello";
        str2 = "Otus!";

        System.out.println(Joiner.on(" ").join(Arrays.asList(str1, str2)));
    }
}
