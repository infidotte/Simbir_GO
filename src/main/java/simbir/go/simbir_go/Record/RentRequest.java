package simbir.go.simbir_go.Record;

import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.RequestBody;

public record RentRequest (Long transportId,
                           Long userId,
                           String timeStart,
                           @Nullable String timeEnd,
                           Double priceOfUnit,
                           String priceType,
                           @Nullable Double finalPrice){
}
