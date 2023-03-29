package Protocol;

public interface IProtocol {
    String request (String request) throws Throwable;
    String responce (String rescponce) throws Throwable;
}
