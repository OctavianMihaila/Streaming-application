package system;

import manager.DatabaseManager;
import manager.ObjectNotFoundException;
import model.AccountTemplate;

/**
 * Performs list for streams based on the account type(user or streamer).
 */
public class ListAccount implements Command {
    private final String[] attributes;

    public ListAccount(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();

        try {
            AccountTemplate account = dbManager.findAccount(Integer.parseInt(attributes[0]));
            account.listStreams(dbManager.getStreams());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
