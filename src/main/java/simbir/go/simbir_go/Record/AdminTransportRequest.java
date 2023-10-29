package simbir.go.simbir_go.Record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

public class AdminTransportRequest extends TransportRequest {
    @NotNull Long ownerId;

    public AdminTransportRequest(@NotNull Long ownerId,
                                 @NotNull Boolean canBeRented,
                                 @NotBlank String transportType,
                                 @NotBlank String model,
                                 @NotBlank String color,
                                 @NotBlank String identifier,
                                 String description,
                                 @NotNull Double latitude,
                                 @NotNull Double longitude,
                                 Double minutePrice,
                                 Double dayPrice) {
        super(canBeRented, transportType, model, color, identifier, description, latitude, longitude, minutePrice, dayPrice);
        this.ownerId = ownerId;
    }
}
