package by.logoped.logopedservice.exception;

public class NoActivatedAccountException extends RuntimeException{
    public NoActivatedAccountException() {
    }

    public NoActivatedAccountException(String message) {
        super(message);
    }
}