package io.github.ital023.BaconItaloMirandaAV3FBUNI.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.exceptions.CsvValidationException;
import io.github.ital023.BaconItaloMirandaAV3FBUNI.controller.dto.ConnectionResponse;
import io.github.ital023.BaconItaloMirandaAV3FBUNI.models.Actor;
import io.github.ital023.BaconItaloMirandaAV3FBUNI.models.CastMember;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BaconService {

    private final Map<String, Actor> graph = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void buildGraphFromCsv() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/tmdb_5000_credits.csv");
             CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.UTF_8))
                     .withCSVParser(new RFC4180Parser())
                     .build()){

            reader.readNext();
            String[] line;

            while ((line = reader.readNext()) != null) {
                String movieTitle = line[1];
                String castJson = line[2];

                if (castJson == null || castJson.isEmpty() || castJson.equals("[]")) {
                    continue;
                }

                try {
                    List<CastMember> cast = objectMapper.readValue(castJson, new TypeReference<>() {
                    });
                    List<Actor> movieActors = new ArrayList<>();


                    for (CastMember member : cast) {
                        Actor actor = graph.computeIfAbsent(member.getName(), Actor::new);
                        movieActors.add(actor);
                    }


                    for (int i = 0; i < movieActors.size(); i++) {
                        for (int j = i + 1; j < movieActors.size(); j++) {
                            Actor actor1 = movieActors.get(i);
                            Actor actor2 = movieActors.get(j);
                            actor1.addConnection(actor2, movieTitle);
                            actor2.addConnection(actor1, movieTitle);
                        }
                    }

                } catch (Exception e) {

                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Falha ao ler ou processar o arquivo CSV.", e);
        }
    }

    public ConnectionResponse findConnection(String startActorName, String endActorName) {
        Actor startActor = graph.get(startActorName);
        Actor endActor = graph.get(endActorName);

        if (startActor == null) return ConnectionResponse.notFound("Ator de partida não encontrado: " + startActorName);
        if (endActor == null) return ConnectionResponse.notFound("Ator de destino não encontrado: " + endActorName);

        Queue<Actor> queue = new LinkedList<>();
        Map<Actor, Actor.Connection> pathTracker = new HashMap<>();

        queue.add(startActor);
        pathTracker.put(startActor, null);

        while (!queue.isEmpty()) {
            Actor currentActor = queue.poll();

            if (currentActor.equals(endActor)) {
                return ConnectionResponse.found(reconstructPath(endActor, pathTracker));
            }

            for (Actor.Connection connection : currentActor.getConnections()) {
                Actor neighbor = connection.actor();
                if (!pathTracker.containsKey(neighbor)) {
                    pathTracker.put(neighbor, new Actor.Connection(currentActor, connection.movie()));
                    queue.add(neighbor);
                }
            }
        }

        return ConnectionResponse.notFound("Nenhuma conexão encontrada entre " + startActorName + " e " + endActorName + ".");
    }


    private List<String> reconstructPath(Actor endActor, Map<Actor, Actor.Connection> pathTracker) {
        LinkedList<String> path = new LinkedList<>();
        Actor current = endActor;

        while (current != null) {
            path.addFirst(current.getName());
            Actor.Connection predecessor = pathTracker.get(current);
            if (predecessor != null) {
                path.addFirst(" << " + predecessor.movie() + " >> ");
                current = predecessor.actor();
            } else {
                current = null;
            }
        }
        return path;
    }


}

