package system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import manager.DatabaseManager;
import manager.ObjectNotFoundException;
import model.AudioStream;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PreferenceStream implements Command {

    private static final int TOP_SUGGESTIONS = 5;

    private final String[] attributes;

    public PreferenceStream(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        List<AudioStream> recommendedStreams = new ArrayList<>();

        try {
            User user = dbManager.findUser(Integer.parseInt(attributes[0]));

            // Removing the streams that the user which he listened to.
            for (Integer streamId : user.getStreams()) {
                AudioStream stream = dbManager.findStream(streamId);
                Integer streamerId = stream.getStreamerId();
                List<AudioStream> allStreams = dbManager.findStreams(streamerId);

                allStreams.remove(stream);
                recommendedStreams.addAll(allStreams);
            }
            Collections.sort(recommendedStreams, new Comparator<AudioStream>() { // Sort by nrOfStreams.
                @Override
                public int compare(AudioStream s1, AudioStream s2) {
                    return s1.getNoOfStreams().compareTo(s2.getNoOfStreams());
                }
            });

            int topSuggestions = Math.min(TOP_SUGGESTIONS, recommendedStreams.size());
            JsonArray jsonStreams = new JsonArray();

            for (AudioStream stream : recommendedStreams.subList(0, topSuggestions)) {
                JsonObject jsonStream = stream.toJSON();

                jsonStream.addProperty("streamerName", dbManager.findStreamer(stream.getStreamerId()).getName());
                jsonStreams.add(jsonStream);
            }

            System.out.println(jsonStreams);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
