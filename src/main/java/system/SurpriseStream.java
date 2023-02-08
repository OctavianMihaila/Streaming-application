package system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import manager.DatabaseManager;
import manager.ObjectNotFoundException;
import model.AudioStream;
import model.User;

import java.util.*;

public class SurpriseStream implements Command {

    private static final int TOP_SUGGESTIONS = 3;

    private final String[] attributes;

    public SurpriseStream(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        List<AudioStream> recommendedStreams = new ArrayList<>();

        try {
            User user = dbManager.findUser(Integer.parseInt(attributes[0]));
            List<Integer> userStremears = new ArrayList<>();

            for (Integer streamId : user.getStreams()) { // Select streamers that the user listened to.
                AudioStream stream = dbManager.findStream(streamId);
                userStremears.add(stream.getStreamerId());
            }

            for (Map.Entry<Integer, ArrayList<AudioStream>> entry : dbManager.getStreams().entrySet()) {
                for (AudioStream stream : entry.getValue()) {
                    if (!userStremears.contains(stream.getStreamerId())) {
                        recommendedStreams.add(stream);
                    }
                }
            }
            // Sort streams by date as first criteria and noOfStreams as 2nd.
            Collections.sort(recommendedStreams, new Comparator<AudioStream>() {
                @Override
                public int compare(AudioStream s1, AudioStream s2) {
                    if (s1.getDateAdded().equals(s2.getDateAdded())) {
                        return s2.getNoOfStreams().compareTo(s1.getNoOfStreams());
                    } else {
                        return s2.getDateAdded().compareTo(s1.getDateAdded());
                    }
                }
            });

            int topSuggestions = Math.min(TOP_SUGGESTIONS, recommendedStreams.size());
            JsonArray jsonStreams = new JsonArray();

            for (AudioStream stream : recommendedStreams.subList(0, topSuggestions)) {
                JsonObject jsonStream = stream.toJSON();

                jsonStream.addProperty("streamerName", dbManager.
                        findStreamer(stream.getStreamerId()).getName());
                jsonStreams.add(jsonStream);
            }

            System.out.println(jsonStreams);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
