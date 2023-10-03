# Streaming Application with Content Recommendation

## Description

This streaming application is designed for music, podcasts, and audiobooks, and it incorporates advanced content recommendation algorithms. These algorithms leverage existing user and streamer data, as well as real-time usage statistics.

## Structure

### Application Data

The application stores its data in files, which should be provided as parameters to the main method in the following order: `streamers.csv`, `streams.csv`, `users.csv`, `comenzi.txt`.

### Key Commands

The streaming application offers users and streamers various commands for creating and managing streams:

- **Add Stream:** Modifies the application data without printing anything to the console.

- **List:** Lists streams based on the account type (user or streamer).

- **Delete Stream:** Modifies the application data without printing anything to the console.

- **Listen to a Stream:** Modifies the application data without printing anything to the console.

- **Preference Stream:** Recommends streams based on the user's listening history. It selects the top 5 unlistened streams with the most listens for a specific stream type (SONG, PODCAST, or AUDIOBOOK).

- **Surprise Stream:** Recommends streams based on recent additions. It selects 3 streams added most recently among those not listened to by the user, with preference given to streams with more listens. Recommendations are made for a specific stream type (SONG, PODCAST, or AUDIOBOOK).

## Implementation

The application is implemented using the following design patterns:

- **Singleton:** Utilized for the `DatabaseManager` and `RecommendationSystem` classes to ensure that only one instance of each is created. This promotes memory efficiency and facilitates sharing of the class.

- **Factory:** Employed for instantiating accounts (User or Streamer), allowing subclasses to determine the type of objects to create and promoting loose coupling.

- **Command:** Used to decouple classes invoking operations from those that execute them. All commands in the system, including `InitSystem` and `ClearSystem` for database management, are designed using this pattern.

- **Builder:** Applied to the `AudioStream` class, enabling flexibility in varying an object's internal representation. It encapsulates construction and representation code, which is particularly useful due to the large number of parameters in the `AudioStream` class.

- **Template:** Introduced to prevent code duplication and enhance flexibility by allowing subclasses to define the implementation of steps in the algorithm. This design choice anticipates future extensions to the application, providing a better design for the `Account` class.

