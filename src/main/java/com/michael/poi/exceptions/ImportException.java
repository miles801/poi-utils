package com.michael.poi.exceptions;

/**
 * 导入异常
 *
 * @author Michael
 */
public class ImportException extends RuntimeException {
    public ImportException() {
        super();
    }

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportException(Throwable cause) {
        super(cause);
    }

}
