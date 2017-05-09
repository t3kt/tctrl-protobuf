package net.t3kt.tctrl.schema;

public class SchemaValidationException extends RuntimeException {
    public SchemaValidationException() {
    }

    public SchemaValidationException(String message) {
        super(message);
    }

    public SchemaValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaValidationException(Throwable cause) {
        super(cause);
    }
}
