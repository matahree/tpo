package Exception;

public class HandleException extends Exception {

    private static final long serialVersionUID = 1L;

    public HandleException(String message) {
        super(message);
    }
    public HandleException(Throwable throwable){
        super(throwable);
    }
}
