package system;

import manager.DatabaseManager;
import model.AudioStream;
import utils.DateAndTimeConvertor;

/**
 * Adds a stream to the system database.
 */
public class AddStream implements Command {

    private final String[] attributes;

    public AddStream(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        AudioStream newStream = new AudioStream.StreamBuilder(Integer.parseInt(attributes[3]))
                .streamType(Integer.parseInt(attributes[2]))
                .streamGenre(Integer.parseInt(attributes[4]))
                .noOfStreams(0L)
                .streamerId(Integer.parseInt(attributes[0]))
                .length(Long.parseLong(attributes[5]))
                .dateAdded(DateAndTimeConvertor.convertToUnixDate("13-01-2023"))
                .name(attributes[6]).build();

        DatabaseManager.getInstance().addStream(newStream);
    }
}
