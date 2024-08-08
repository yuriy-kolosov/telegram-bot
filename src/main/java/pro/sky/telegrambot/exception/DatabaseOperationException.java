package pro.sky.telegrambot.exception;

public class DatabaseOperationException extends RuntimeException {

    private final String message;

    public DatabaseOperationException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DatabaseOperationError{" +
                "message='" + message + '\'' +
                '}';
    }

}
