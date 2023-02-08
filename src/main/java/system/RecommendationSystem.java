package system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Used Command design pattern to create a sequence of commands and to decouple the classes
 * that invoke the operation from the object that knows how to execute the operation
 */
public class RecommendationSystem {
    private static RecommendationSystem instance;
    private final List<Command> commands = new ArrayList<>();

    public static RecommendationSystem getInstance() {
        if (instance == null) {
            instance = new RecommendationSystem();
        }
        return instance;
    }

    public void addCommand(Command newCommand) {
        commands.add(newCommand);
    }

    public void executeCommands() {
        Iterator<Command> commandsIterator = commands.iterator();

        while (commandsIterator.hasNext()) {
            Command command = commandsIterator.next();

            command.execute();
            commandsIterator.remove();
        }
    }
}
