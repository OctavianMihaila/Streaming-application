# Description
        
        -> Streaming application (music, podcast, audiobook), which includes content recommendation
           algorithms based on existing data about the application's users (listeners) and streamers
           or accumulated over the course of the run the application.


# Structure

        -> Application data (is stored in files whose names are passed as parameters to the main
           method in the following order: streamers.csv, streams.csv, users.csv, comenzi.txt.
           
        -> The streaming application allows the users and streamers to create and manage streams
           using some commands. The most important of the commands are:   
            
            >> Add Stream: Nothing is printed to the console, but the application data is modified.
            
            >> List: List streams according to account type (user or streamer).
            
            >> Delete stream: Nothing is printed to the console, but the application data is modified.
            
            >> Listen to a stream: Nothing is printed to the console, but the application data is modified.
            
            >> Preference stream: The recommendation algorithm for streams will be as follows:
               From the list of streamers listened to by the user, you will choose the top 5 (unlistened)
               streams with the most listens. The recommendation will be made for the type of stream
               passed as a parameter (SONG, PODCAST or AUDIOBOOK).
               
            >> Surpirse stream: The stream recommendation algorithm will be as follows:
               From the list of streamers in the application, which have not been listened to by the user,
               you will choose 3 streams that were added most recently. If they were added on the same day,
               then you will choose the stream with those more listening. The recommendation will be made
               for the type of stream passed as a parameter (SONG, PODCAST or AUDIOBOOK).

# Implementation

        -> Data parsing in the application is done using OpenCsv (com.opencsv:opencsv:5.7.1) and
           display in the console using data in the form of JSON (com.google.code.gson:gson:2.8.6).

        -> The application is designed using 5 design patterns(Singleton, Factory, Command, Builder, Template):

                >> Singleton is used for DatabaseManager and RecommendationSystem classes in order
                   to restrict the limit of the number of object creation to only one. It brings
                   efficiency in terms of used memory and allows the code to share a class more easily.

                >> Factory design pattern is used for Account instantiation (User or Streamer).
                   It allows the sub-classes to choose the type of objects to create. It promotes
                   the loose-coupling by eliminating the need to bind application-specific classes
                   into the code.


                >> Command is used to decouple the classes that invoke the operation from the object
                   that knows how to execute the operation. All the command in the system are designed
                   using this pattern, including InitSystem and ClearSystem that manages the database.

                >> Builder is used for AudioStream class and allows to vary a object's internal
                   representation. Encapsulates code for construction and representation. I picked
                   this design because of the large number of parameters the AudioStream class has.

                >> The role of the template is to prevent duplicate code, but it also brings flexibility
                   by letting subclasses decide how to implement steps in the algorithm. I chose this
                   design thinking about a possible extension of the application that would allow a
                   better design for the Account class
