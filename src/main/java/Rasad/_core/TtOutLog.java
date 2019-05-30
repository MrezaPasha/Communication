package main.java.Rasad._core;

public enum TtOutLog {

    Exception("E"),
    Info("I"),
    FatalException("FE"),
    FatalInfo("FI"),
    Warning("W"),
    SysInit("SI");

    private final String key;

    private TtOutLog(String k) {
        key = k;
    }

    public String getKey() {
        return key;
    }
}
