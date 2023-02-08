package manager;

public class IncorrectAccountTypeException extends Exception {

    public IncorrectAccountTypeException(String providedAccountType) {
        super("Provided user type is wrong: " + providedAccountType);
    }
}
