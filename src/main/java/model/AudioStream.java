package model;

import com.google.gson.JsonObject;
import utils.DateAndTimeConvertor;

/**
 * Used build design pattern in order to separate the construction of a complex object from
 * its representation so that the same construction process can create different representations.
 */
public class AudioStream {
    private final Integer streamType;
    private final Integer id;
    private final Integer streamGenre;
    private Long noOfStreams;
    private final Integer streamerId;
    private final Long length;
    private final Long dateAdded;
    private final String name;

    public AudioStream(StreamBuilder builder) {
        this.streamType = builder.streamType;
        this.id = builder.id;
        this.streamGenre = builder.streamGenre;
        this.noOfStreams = builder.noOfStreams;
        this.streamerId = builder.streamerId;
        this.length = builder.length;
        this.dateAdded = builder.dateAdded;
        this.name = builder.name;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public Integer getId() {
        return id;
    }

    public Long getNoOfStreams() {
        return noOfStreams;
    }

    public void listenStream() {
        noOfStreams++;
    }

    public Integer getStreamerId() {
        return streamerId;
    }

    public Long getLength() {
        return length;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    public JsonObject toJSON() {
        JsonObject jsonStream = new JsonObject();

        jsonStream.addProperty("id", String.valueOf(id));
        jsonStream.addProperty("name", name);
        jsonStream.addProperty("streamerName", "null");
        jsonStream.addProperty("noOfListenings", String.valueOf(noOfStreams));
        jsonStream.addProperty("length", DateAndTimeConvertor.converTime(length));
        jsonStream.addProperty("dateAdded", DateAndTimeConvertor.convertToHumanReadableDate(dateAdded));

        return jsonStream;
    }

    @Override
    public String toString() {
        return "AudioStream{" +
                "streamType=" + streamType +
                ", id=" + id +
                ", streamGenre=" + streamGenre +
                ", noOfStreams=" + noOfStreams +
                ", streamerId=" + streamerId +
                ", length=" + length +
                ", dateAdded=" + dateAdded +
                ", name='" + name + '\'' +
                '}';
    }

    public static class StreamBuilder {
        private Integer streamType;
        private final Integer id;
        private Integer streamGenre;
        private Long noOfStreams;
        private Integer streamerId;
        private Long length;
        private Long dateAdded;
        private String name;

        public StreamBuilder(Integer id) {
            this.id = id;
        }

        public StreamBuilder streamType(Integer streamType) {
            this.streamType = streamType;
            return this;
        }

        public StreamBuilder streamGenre(Integer streamGenre) {
            this.streamGenre = streamGenre;
            return this;
        }

        public StreamBuilder noOfStreams(Long noOfStreams) {
            this.noOfStreams = noOfStreams;
            return this;
        }

        public StreamBuilder streamerId(Integer streamerId) {
            this.streamerId = streamerId;
            return this;
        }

        public StreamBuilder length(Long length) {
            this.length = length;
            return this;
        }

        public StreamBuilder dateAdded(Long dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public StreamBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AudioStream build() {
            return new AudioStream(this);
        }
    }
}
