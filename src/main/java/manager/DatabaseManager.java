package manager;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.AccountTemplate;
import model.AudioStream;
import model.Streamer;
import model.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Singleton class that provides methods for interacting with the database
 * (init, read data from files, find specific objects in lists / maps, clear).
 */
public class DatabaseManager {
    private static DatabaseManager DbManager;
    private Map<Integer, ArrayList<AudioStream>> streams;
    private Map<Integer, ArrayList<Streamer>> streamers;
    private List<User> users;

    public DatabaseManager() {
        streamers = null;
        streams = null;
        users = null;
    }

    public static DatabaseManager getInstance() {
        if (DbManager == null) {
            DbManager = new DatabaseManager();
        }

        return DbManager;
    }

    public CSVReader initCSVReader(String filePath) {
        FileReader filereader = null;
        CSVReader csvReader = null;

        try {
            filereader = new FileReader(filePath);
            csvReader = new CSVReader(filereader);
            csvReader.readNext(); // Ignore first line, which is the template.
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return csvReader;
    }

    /**
     *
     * @param filePath
     * @param type List -> reads users, Map -> reads streamers
     * @return
     * @param <T> List or Map, based on the provided type
     * @throws IllegalArgumentException
     */
    public <T> T readAccounts(String filePath, Class<T> type) throws IllegalArgumentException {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        String[] nextLine;
        CSVReader csvReader = this.initCSVReader(filePath);

        try {
            while ((nextLine = csvReader.readNext()) != null) {
                try {
                    if (type == Map.class) {
                        if (this.streamers == null) {
                            this.streamers = new HashMap<>();
                        }
                        Streamer newStreamer = (Streamer) AccountFactory.createNewAccount(fileName, nextLine);
                        Integer streamerType = newStreamer.getStreamerType();

                        this.streamers.computeIfAbsent(streamerType, k -> new ArrayList<>()).add(newStreamer);
                    } else if (type == List.class) {
                        if (this.users == null) {
                            this.users = new ArrayList<>();
                        }
                        User newUser = (User) AccountFactory.createNewAccount(fileName, nextLine);

                        this.users.add(newUser);
                    } else {
                        throw new IllegalArgumentException("Invalid type: " + type);
                    }
                } catch (IncorrectAccountTypeException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        if (type == List.class) {
            return (T) this.users;
        } else {
            return (T) this.streamers;
        }
    }

    public Map<Integer, ArrayList<AudioStream>> readStreams(String filePath) {
        String[] nextLine;
        CSVReader csvReader = this.initCSVReader(filePath);

        try {
            while ((nextLine = csvReader.readNext()) != null) {
                if (this.streams == null) {
                    this.streams = new HashMap<>();
                }

                AudioStream newStream = new AudioStream.StreamBuilder(Integer.parseInt(nextLine[1]))
                        .streamType(Integer.parseInt(nextLine[0]))
                        .streamGenre(Integer.parseInt(nextLine[2]))
                        .noOfStreams(Long.parseLong(nextLine[3]))
                        .streamerId(Integer.parseInt(nextLine[4]))
                        .length(Long.parseLong(nextLine[5]))
                        .dateAdded(Long.parseLong(nextLine[6]))
                        .name(nextLine[7]).build();

                this.streams.computeIfAbsent(newStream.getStreamType(), k -> new ArrayList<>()).add(newStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return this.streams;
    }

    public Streamer findStreamer(Integer stremerId) throws ObjectNotFoundException {
        Iterator<Map.Entry<Integer, ArrayList<Streamer>>> iterator = this.streamers.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, ArrayList<Streamer>> entry = iterator.next();
            ArrayList<Streamer> streamersWithSpecificType = entry.getValue();

            for (Streamer streamer : streamersWithSpecificType) {
                if (streamer.getId().equals(stremerId)) {
                    return streamer;
                }
            }
        }

        throw new ObjectNotFoundException(Streamer.class.getName(), String.valueOf(stremerId));
    }

    public User findUser(Integer userId) throws ObjectNotFoundException {
        for (User user : this.users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        throw new ObjectNotFoundException(User.class.getName(), String.valueOf(userId));
    }

    public AccountTemplate findAccount(Integer id) throws ObjectNotFoundException {
        try {
            return findStreamer(id);
        } catch (ObjectNotFoundException e1) {
            try {
                return findUser(id);
            } catch (ObjectNotFoundException e2) {
                throw new ObjectNotFoundException(User.class.getName(), String.valueOf(id));
            }
        }
    }

    public AudioStream findStream(Integer streamId) throws ObjectNotFoundException {

        for (Map.Entry<Integer, ArrayList<AudioStream>> entry : streams.entrySet()) {
            for (AudioStream stream : entry.getValue()) {
                if (stream.getId().equals(streamId)) {
                    return stream;
                }
            }
        }

        throw new ObjectNotFoundException(AudioStream.class.getName(), String.valueOf(streamId));
    }

    public List<AudioStream> findStreams(Integer streamerId) throws ObjectNotFoundException {
        List<AudioStream> foundStreams = new ArrayList<>();

        for (Map.Entry<Integer, ArrayList<AudioStream>> entry : streams.entrySet()) {
            for (AudioStream stream : entry.getValue()) {
                if (stream.getStreamerId().equals(streamerId)) {
                    foundStreams.add(stream);
                }
            }
        }

        return foundStreams;
    }

    public void addStream(AudioStream newStream) {
        streams.get(newStream.getStreamType()).add(newStream);
    }

    public Map<Integer, ArrayList<AudioStream>> getStreams() {
        return streams;
    }

    public void clear() {
        streams.clear();
        streamers.clear();
        users.clear();
    }
}