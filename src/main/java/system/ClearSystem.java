package system;

import manager.DatabaseManager;

/**
 * Removes data from the system database.
 */
public class ClearSystem implements Command {
    @Override
    public void execute() {
        DatabaseManager.getInstance().clear();
    }
}
