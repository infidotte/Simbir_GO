package simbir.go.simbir_go.Record;

import lombok.Getter;

@Getter
public class AdminAuthRequest extends AuthRequest{
    private final Boolean isAdmin;
    private final Double balance;
    public AdminAuthRequest(String username, String password, Boolean isAdmin, Double balance) {
        super(username, password);
        this.isAdmin = isAdmin;
        this.balance = balance;
    }
}
