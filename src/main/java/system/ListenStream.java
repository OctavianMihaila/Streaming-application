package system;

import manager.DatabaseManager;
import manager.ObjectNotFoundException;
import model.AudioStream;
import model.User;

/**
 * Performs update to the system database when a stream is listened.
 */
public class ListenStream implements Command {

    private final String[] attributes;

    public ListenStream(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();

        try {
            User user = dbManager.findUser(Integer.parseInt(attributes[0]));
            Integer streamId = Integer.parseInt(attributes[2]);

            user.addListenedStream(streamId);
            AudioStream stream = dbManager.findStream(streamId);
            stream.listenStream();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
