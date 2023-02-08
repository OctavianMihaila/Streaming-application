package model;

import java.util.ArrayList;
import java.util.Map;

public abstract class AccountTemplate {

    private final Integer id;
    private final String name;

    public AccountTemplate(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void listStreams(Map<Integer, ArrayList<AudioStream>> streams);

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
