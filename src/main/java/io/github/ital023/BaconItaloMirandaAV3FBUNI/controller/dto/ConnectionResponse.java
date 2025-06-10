package io.github.ital023.BaconItaloMirandaAV3FBUNI.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionResponse {

    private final String message;
    private final List<String> path;

    public ConnectionResponse(String message, List<String> path) {
        this.message = message;
        this.path = path;
    }

    public static ConnectionResponse found(List<String> path) {
        return new ConnectionResponse("Conexão encontrada!", path);
    }

    public static ConnectionResponse notFound(String message) {
        return new ConnectionResponse(message, null);
    }

    public String getMessage() {
        return message;
    }

    public List<String> getPath() {
        return path;
    }
}