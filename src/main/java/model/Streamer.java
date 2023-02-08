package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import manager.DatabaseManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Streamer extends AccountTemplate {

    private final Integer streamerType;

    public Streamer(Integer streamerType, Integer id, String name) {
        super(id, name);
        this.streamerType = streamerType;
    }

    public Integer getStreamerType() {
        return streamerType;
    }

    /**
     * Lists the streams of a streamer in json format.
     * @param streams
     */
    @Override
    public void listStreams(Map<Integer, ArrayList<AudioStream>> streams) {
        JsonArray jsonStreams = new JsonArray();
        Iterator<Map.Entry<Integer, ArrayList<AudioStream>>> iterator = streams.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, ArrayList<AudioStream>> entry = iterator.next();
            ArrayList<AudioStream> streamsWithSpecificType = entry.getValue();

            for (AudioStream stream : streamsWithSpecificType) {
                if (stream.getStreamerId().equals(this.getId())) {
                    DatabaseManager dbManager = new DatabaseManager();
                    JsonObject jsonStream = stream.toJSON();

                    jsonStream.addProperty("streamerName", this.getName());
                    jsonStreams.add(jsonStream);
                }
            }
        }

        System.out.println(jsonStreams);
    }

    @Override
    public String toString() {
        return "Streamer{" +
                "streamerType=" + streamerType +
                "} " + super.toString();
    }
}
