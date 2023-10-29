package simbir.go.simbir_go.Utilit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import simbir.go.simbir_go.Entity.Account;

import java.io.IOException;

public class AccountSerializer extends StdSerializer<Account> {
    public AccountSerializer() {
        this(null);
    }

    protected AccountSerializer(Class<Account> t) {
        super(t);
    }

    @Override
    public void serialize(Account value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("password", value.getPassword());
        gen.writeStringField("username", value.getUsername());
        gen.writeStringField("role", value.getRole().getName());
        gen.writeNumberField("balance", value.getBalance());
        gen.writeEndObject();
    }
}
