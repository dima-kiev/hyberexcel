package com.github.dima.kiev.hyberexcel.exceptions;

public class NoSuchSheetException extends RuntimeException {

    public NoSuchSheetException() {
        super();
    }

    public NoSuchSheetException(String msg) {
        super(msg);
    }

    public NoSuchSheetException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
