package pl.martialdb.app.exceptions;

public class MethodNotSupportedException extends Exception{
    private static final long serialVersionUID = -4982904457486342338L;

    public MethodNotSupportedException(String message) {
        super(message);
    }
}
