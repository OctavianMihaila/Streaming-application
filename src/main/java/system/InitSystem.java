package system;

import manager.DatabaseManager;

import java.util.List;
import java.util.Map;

/**
 * Reads data from the input files and maps it into objects(streams, streamers, users).
 */
public class InitSystem implements Command {
    private static final String INPUT_PATH = "src/main/resources/";

    private final String[] attributes;

    public InitSystem(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();

        dbManager.readAccounts(INPUT_PATH + attributes[0], Map.class);
        dbManager.readStreams(INPUT_PATH + attributes[1]);
        dbManager.readAccounts(INPUT_PATH + attributes[2], List.class);
    }
}
