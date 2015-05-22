package com.michael.poi.exceptions;

/**
 * 导入配置异常
 *
 * @author Michael
 */
public class ImportConfigException extends ImportException {
    public ImportConfigException() {
        super();
    }

    public ImportConfigException(String message) {
        super(message);
    }

    public ImportConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportConfigException(Throwable cause) {
        super(cause);
    }

}
