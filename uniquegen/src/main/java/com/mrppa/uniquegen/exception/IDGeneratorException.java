package com.mrppa.uniquegen.exception;

public class IDGeneratorException extends RuntimeException {
    public IDGeneratorException(String errorMsg) {
        super(errorMsg);
    }

    public IDGeneratorException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
