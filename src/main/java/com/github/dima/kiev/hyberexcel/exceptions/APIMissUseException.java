package com.github.dima.kiev.hyberexcel.exceptions;

public class APIMissUseException extends RuntimeException {

    public APIMissUseException(Throwable cause) {
        super("Please use {} after handler class construction. This is the only way to remember about generics in Java runtime", cause);
    }

    // todo  remove this hardcode
    public APIMissUseException() {
        super("Please use {} after handler class construction. This is the only way to remember about generics in Java runtime");
    }

    public APIMissUseException(String msg) {
        super(msg);
    }

}
