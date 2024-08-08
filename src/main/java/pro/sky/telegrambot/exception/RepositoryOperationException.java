package pro.sky.telegrambot.exception;

public class RepositoryOperationException extends RuntimeException {

    private final String message;

    public RepositoryOperationException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DatabaseOperationError{" +
                "message='" + message + '\'' +
                '}';
    }

}
