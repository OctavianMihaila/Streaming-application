package manager;

import model.AccountTemplate;
import model.Streamer;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Used Factory design pattern to create accounts based on the provided types.
 */
public class AccountFactory {

    public static AccountTemplate createNewAccount(String accountType, String[] attributes)
            throws IncorrectAccountTypeException {

        switch (accountType) {
            case "streamers":
                return new Streamer(Integer.parseInt(attributes[0]),
                        Integer.parseInt(attributes[1]), attributes[2]);

            case "users":
                return new User(Integer.parseInt(attributes[0]), attributes[1], parseStreamIds(attributes));

            default:
                throw new IncorrectAccountTypeException(accountType);
        }
    }

    private static ArrayList<Integer> parseStreamIds(String[] attributes) {
        String[] splitedIds = attributes[2].split(" ");
        ArrayList<Integer> streamIds = new ArrayList<>();

        Arrays.stream(splitedIds).map(id -> Integer.parseInt(id)).forEach(streamIds::add);

        return streamIds;
    }
}
