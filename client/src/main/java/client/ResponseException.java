package client;

public class ResponseException extends RuntimeException {
    private final int status;
    public ResponseException(int status) {
        super("Request failed by status " + status);
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
}
