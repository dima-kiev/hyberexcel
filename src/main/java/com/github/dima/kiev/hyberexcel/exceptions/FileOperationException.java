package com.github.dima.kiev.hyberexcel.exceptions;

public class FileOperationException extends RuntimeException{

    public FileOperationException(String msg) {
        super(msg);
    }

    public FileOperationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FileOperationException(Throwable cause) {
        super(cause);
    }

}
