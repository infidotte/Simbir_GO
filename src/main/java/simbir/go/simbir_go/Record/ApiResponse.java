package simbir.go.simbir_go.Record;

import lombok.Getter;

import java.util.AbstractMap;

@Getter
public class ApiResponse {
    private final AbstractMap.SimpleEntry<String, String> entry;

    public ApiResponse(String key, String value) {
        this.entry = new AbstractMap.SimpleEntry<>(key, value);
    }
}

