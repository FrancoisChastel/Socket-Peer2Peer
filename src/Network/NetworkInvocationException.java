package Network;

/**
 * @author François Chastel
 */
public class NetworkInvocationException extends RuntimeException {
    public NetworkInvocationException() {
        super();
    }

    public NetworkInvocationException(String message) {
        super(message);
    }

    public NetworkInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}