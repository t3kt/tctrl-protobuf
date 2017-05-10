package net.t3kt.tctrl.schema.json;

public class SchemaParsingException extends RuntimeException {
    public SchemaParsingException() {
    }

    public SchemaParsingException(String message) {
        super(message);
    }

    public SchemaParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaParsingException(Throwable cause) {
        super(cause);
    }
}
