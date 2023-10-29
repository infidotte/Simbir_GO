package simbir.go.simbir_go.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Rent;
import simbir.go.simbir_go.Entity.Transport;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.RentNotFoundException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Service.RentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Rent")
public class RentController {
    private final RentService rentService;

    //описание: Получение транспорта доступного для аренды по параметрам
    //ограничения: нет
    @GetMapping("/Transport")
    public ResponseEntity<List<Transport>> getTransportForRent(@RequestParam Double latitude,
                                                               @RequestParam Double longitude,
                                                               @RequestParam Double radius,
                                                               @RequestParam String type
    ) {

        return ResponseEntity.ok(rentService.getTransportForRent(latitude, longitude, radius, type));
    }

    //описание: Получение информации об аренде по id
    //ограничения: Только арендатор и владелец транспорта
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{rentId}")
    public ResponseEntity<Rent> getRentById(@PathVariable String rentId) throws MethodNotAllowedException, RentNotFoundException {
        return ResponseEntity.ok(rentService.getRentById(Long.parseLong(rentId)));
    }

    //описание: Получение истории аренд текущего аккаунта
    //ограничения: Только авторизованные пользователи
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/MyHistory")
    public ResponseEntity<List<Rent>> getCurrentUserRentHistory() {
        return ResponseEntity.ok(rentService.getCurrentUserRentHistory());
    }

    //описание: Получение истории аренд транспорта
    //ограничения: Только владелец этого транспорта
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/TransportHistory/{transportId}")
    public ResponseEntity<List<Rent>> getTransportRentHistory(@PathVariable String transportId) throws TransportNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(rentService.getTransportRentHistory(Long.parseLong(transportId)));
    }

    //описание: Аренда транспорта в личное пользование
    //ограничения: Только авторизованные пользователи, нельзя брать в аренду
    //собственный транспорт
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/New/{transportId}")
    public ResponseEntity<Rent> startRent(@PathVariable String transportId,
                                          @RequestParam String rentType
    ) throws TransportNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(rentService.startRent(Long.parseLong(transportId), rentType));
    }

    //описание: Завершение аренды транспорта по id аренды
    //ограничения: Только человек который создавал эту аренду
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/End/{rentId}")
    public ResponseEntity<Rent> endRent(@PathVariable String rentId,
                                        @RequestParam Double latitude,
                                        @RequestParam Double longitude
    ) throws MethodNotAllowedException, RentNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(rentService.userEndRent(Long.parseLong(rentId), latitude, longitude));
    }
}
