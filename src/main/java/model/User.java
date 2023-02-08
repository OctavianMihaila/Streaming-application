package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import manager.DatabaseManager;
import manager.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class User extends AccountTemplate {

    private final List<Integer> listenedStreams;

    public User(Integer id, String name, List<Integer> streams) {
        super(id, name);
        this.listenedStreams = streams;
    }

    public void addListenedStream(Integer stream) {
        listenedStreams.add(stream);
    }

    public List<Integer> getStreams() {
        return listenedStreams;
    }

    /**
     * Lists the user's listening history.
     * @param streams
     */
    @Override
    public void listStreams(Map<Integer, ArrayList<AudioStream>> streams) {
        JsonArray jsonStreams = new JsonArray();

        for (Integer searchedId : listenedStreams) {
            Boolean found = false;
            Iterator<Map.Entry<Integer, ArrayList<AudioStream>>> iterator = streams.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Integer, ArrayList<AudioStream>> entry = iterator.next();
                ArrayList<AudioStream> streamsWithSpecificType = entry.getValue();

                for (AudioStream stream : streamsWithSpecificType) {
                    if (searchedId == stream.getId().intValue()) {
                        DatabaseManager DbManager = DatabaseManager.getInstance();
                        JsonObject jsonStream = stream.toJSON();

                        try {
                            jsonStream.addProperty("streamerName",
                                    DbManager.findStreamer(stream.getStreamerId()).getName());
                        } catch (ObjectNotFoundException e) {
                            e.printStackTrace();
                        }
                        jsonStreams.add(jsonStream);
                        found = true;
                        break;
                    }
                }

                if (found == true) {
                    break;
                }
            }
        }

        System.out.println(jsonStreams);
    }

    @Override
    public String toString() {
        return "User{" +
                "streams=" + listenedStreams +
                "} " + super.toString();
    }
}
