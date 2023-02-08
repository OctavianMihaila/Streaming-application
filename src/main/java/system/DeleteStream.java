package system;

import manager.DatabaseManager;
import manager.ObjectNotFoundException;
import model.AudioStream;

import java.util.ArrayList;

/**
 * Deletes a stream from the system database.
 */
public class DeleteStream implements Command {

    private final String[] attributes;

    public DeleteStream(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        try {
            Integer streamerType = dbManager.findStreamer(Integer.parseInt(attributes[0])).getId();
            ArrayList<AudioStream> streamsWithSpeficicType = dbManager.getStreams().get(streamerType);
            AudioStream streamToRemove = dbManager.findStream(Integer.parseInt(attributes[2]));

            if (streamsWithSpeficicType != null) {
                streamsWithSpeficicType.remove(streamToRemove);
            }
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
