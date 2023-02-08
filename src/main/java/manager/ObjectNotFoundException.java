package manager;

public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String objectType, String objectId) {
        super("Provided " + objectType + " does not exist. Provided Id: " + objectId);
    }
}
