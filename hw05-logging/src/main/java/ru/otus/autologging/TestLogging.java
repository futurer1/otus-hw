package ru.otus.autologging;

import ru.otus.autologging.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public int calculation(int param1) {
        return param1;
    }

    @Log
    @Override
    public int calculation(int param1, int param2) {
        return param1 + param2;
    }

    @Log
    @Override
    public String calculation(int param1, int param2, String param3) {
        return String.format("%d %s", param1 - param2, param3);
    }

    // not Log
    @Override
    public int calculation(int param1, int param2, int param3) {
        return param1 + param2 + param3;
    }
}
