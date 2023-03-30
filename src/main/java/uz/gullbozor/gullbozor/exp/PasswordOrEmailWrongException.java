package uz.gullbozor.gullbozor.exp;

public class PasswordOrEmailWrongException extends RuntimeException{
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
