package pl.martialdb.app.exceptions;

public class ObjectNotFoundException extends Exception {
    private static final long serialVersionUID = 757048583035850710L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}