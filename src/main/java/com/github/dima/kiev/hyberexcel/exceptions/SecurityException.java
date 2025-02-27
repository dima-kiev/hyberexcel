package com.github.dima.kiev.hyberexcel.exceptions;

public class SecurityException extends RuntimeException {

    public SecurityException(String msg) {
        super(msg);
    }

    public SecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }

}
