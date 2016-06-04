package pl.martialdb.app.exceptions;

public class UnhandledTypeException extends Exception {
    private static final long serialVersionUID = 632606678644105583L;
    public UnhandledTypeException(String msg) {
        super(msg);
    }
}
