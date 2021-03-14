package ru.otus.hw3;

import ru.otus.hw3.annotations.*;

public class ResearchedClass {

    private Integer myInt = 0;

    public ResearchedClass() {
    }

    @Test
    public void methodOneT() {
        myInt += 100;
    }

    @Test
    public void methodTwoT() {
        myInt += 200;
    }

    @Test
    public void methodFreeT() throws Exception {
        throw new Exception();
    }

    @Before
    public void methodOneB() {
        myInt -= 50;
    }

    @After
    public void methodOneA() {
        myInt += 250;
    }

    @After
    public void methodTwoA() {
        myInt += 5;
    }

    public int getResult() {
        return myInt;
    }
}
