package io.github.ital023.BaconItaloMirandaAV3FBUNI.models;

import java.util.HashSet;
import java.util.Set;

public class Actor {

    private final String name;

    private final Set<Connection> connections = new HashSet<>();

    public record Connection(Actor actor, String movie) {}

    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public void addConnection(Actor actor, String movie) {
        this.connections.add(new Connection(actor, movie));
    }
}
