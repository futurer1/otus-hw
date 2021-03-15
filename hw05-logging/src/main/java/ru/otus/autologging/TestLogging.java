package ru.otus.autologging;

public class TestLogging implements TestLoggingInterface {

    @Override
    public int calculation(int param1) {
        return param1;
    }

    @Override
    public int calculation(int param1, int param2) {
        return param1 + param2;
    }

    @Override
    public String calculation(int param1, int param2, String param3) {
        return String.format("%d %s", param1 - param2, param3);
    }

    @Override
    public int calculation(int param1, int param2, int param3) {
        return param1 + param2 + param3;
    }
}
