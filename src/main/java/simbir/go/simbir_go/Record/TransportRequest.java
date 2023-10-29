package simbir.go.simbir_go.Record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransportRequest {
    @NotNull Boolean canBeRented;
    @NotBlank String transportType;
    @NotBlank String model;
    @NotBlank String color;
    @NotBlank String identifier;
    String description;
    @NotNull Double latitude;
    @NotNull Double longitude;
    Double minutePrice;
    Double dayPrice;
}
