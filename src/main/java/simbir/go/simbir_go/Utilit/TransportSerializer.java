package simbir.go.simbir_go.Utilit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import simbir.go.simbir_go.Entity.Transport;

import java.io.IOException;

public class TransportSerializer extends StdSerializer<Transport> {
    public TransportSerializer() {
        this(null);
    }

    protected TransportSerializer(Class<Transport> t) {
        super(t);
    }

    @Override
    public void serialize(Transport value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeObjectField("owner", value.getOwner());
        gen.writeBooleanField("canBeRented", value.getCanBeRented());
        gen.writeStringField("transportType", value.getTransportType());
        gen.writeStringField("model", value.getModel());
        gen.writeStringField("color", value.getColor());
        gen.writeStringField("identifier", value.getIdentifier());
        gen.writeStringField("description", value.getDescription() != null ? value.getDescription() : "");
        gen.writeNumberField("latitude", value.getLatitude());
        gen.writeNumberField("longitude", value.getLongitude());
        gen.writeNumberField("minutePrice", value.getMinutePrice() != null ? value.getMinutePrice() : 0);
        gen.writeNumberField("dayPrice", value.getDayPrice() != null ? value.getDayPrice() : 0);
        gen.writeEndObject();
    }
}
