package dev.ash.todoapp.customexceptions;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String message) {
        super(message);
    }
}
