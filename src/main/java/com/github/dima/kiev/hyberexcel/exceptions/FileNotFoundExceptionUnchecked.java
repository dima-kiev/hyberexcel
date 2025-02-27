package com.github.dima.kiev.hyberexcel.exceptions;

public class FileNotFoundExceptionUnchecked extends RuntimeException {

    public FileNotFoundExceptionUnchecked(Throwable cause) {
        super(cause);
    }

    public FileNotFoundExceptionUnchecked(String msg) {
        super(msg);
    }

    public FileNotFoundExceptionUnchecked(String msg, Throwable cause) {
        super(msg, cause);
    }


}
