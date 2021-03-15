package ru.otus.autologging;

import ru.otus.autologging.annotations.Log;

public interface TestLoggingInterface {

    @Log
    public int calculation(int param1);

    @Log
    public int calculation(int param1, int param2);

    @Log
    public String calculation(int param1, int param2, String param3);

    // not Log
    public int calculation(int param1, int param2, int param3);
}
