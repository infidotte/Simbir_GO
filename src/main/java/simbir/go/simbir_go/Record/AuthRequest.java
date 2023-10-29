package simbir.go.simbir_go.Record;

import lombok.Getter;

@Getter
public class AuthRequest {
    private final String username;
    private final String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

