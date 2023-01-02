package com.mrppa.uniquegen;

public class IDGeneratorException extends RuntimeException {
    public IDGeneratorException(String errorMsg) {
        super(errorMsg);
    }

    public IDGeneratorException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
