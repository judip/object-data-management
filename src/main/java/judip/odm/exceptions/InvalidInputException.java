package judip.odm.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(Long id) {
        super("Not found", new Throwable("No such object found with given id: "+ id));
    }
}
