import system.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class ProiectPOO {

    private static final String INPUT_PATH = "src/main/resources/";

    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Nothing to read here");

            return;
        }

        BufferedReader reader = null;
        FileReader fileReader = null;
        RecommendationSystem recommendationSystem = RecommendationSystem.getInstance();

        recommendationSystem.addCommand(new InitSystem(args));
        try {
            String line = null;
            fileReader = new FileReader(INPUT_PATH + args[3]);
            reader = new BufferedReader(fileReader);

            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(" ");
                String request = attributes[1];

                switch (request) {
                    case "ADD":
                        recommendationSystem.addCommand(new AddStream(attributes));
                        break;

                    case "LIST":
                        recommendationSystem.addCommand(new ListAccount(attributes));
                        break;

                    case "DELETE":
                        recommendationSystem.addCommand(new DeleteStream(attributes));
                        break;

                    case "LISTEN":
                        recommendationSystem.addCommand(new ListenStream(attributes));
                        break;

                    case "RECOMMEND":
                        recommendationSystem.addCommand(new PreferenceStream(attributes));
                        break;

                    case "SURPRISE":
                        recommendationSystem.addCommand(new SurpriseStream(attributes));
                        break;

                    default:
                        throw new ParseException("Invalid request", 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        recommendationSystem.addCommand(new ClearSystem());
        recommendationSystem.executeCommands();
    }
}
