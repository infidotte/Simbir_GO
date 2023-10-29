package simbir.go.simbir_go.Utilit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import simbir.go.simbir_go.Entity.Rent;

import java.io.IOException;
import java.util.Date;

public class RentSerializer extends StdSerializer<Rent> {

    public RentSerializer() {
        this(null);
    }

    protected RentSerializer(Class<Rent> t) {
        super(t);
    }

    @Override
    public void serialize(Rent value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeNumberField("transport", value.getTransport().getId());
        gen.writeNumberField("renter", value.getUser().getId());
        gen.writeStringField("timeStart", value.getTimeStart());
        gen.writeStringField("timeEnd", value.getTimeEnd() != null ? value.getTimeEnd() : "");
        gen.writeNumberField("priceOfUnit", value.getPriceOfUnit());
        gen.writeStringField("priceType", value.getPriceType());
        gen.writeNumberField("finalPrice", value.getFinalPrice() != null ? value.getFinalPrice() : 0);
        gen.writeEndObject();
    }
}
