package simbir.go.simbir_go.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Rent;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.RentNotFoundException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.RentRequest;
import simbir.go.simbir_go.Service.RentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin")
public class AdminRentController {
    private final RentService rentService;
    //описание: Получение информации по аренде по id
    //ограничения: Только администраторы
    @GetMapping("/Rent/{rentId}")
    public ResponseEntity<Rent> getRentById(@PathVariable String rentId) throws RentNotFoundException {
        return ResponseEntity.ok(rentService.adminGetRentById(Long.parseLong(rentId)));
    }

    //описание: Получение истории аренд пользователя с id={userId}
    //ограничения: Только администраторы
    @GetMapping("/UserHistory/{userId}")
    public ResponseEntity<List<Rent>> getUserHistoryById(@PathVariable String userId) {
        return ResponseEntity.ok(rentService.getUserRentHistory(Long.parseLong(userId)));
    }

    //описание: Получение истории аренд транспорта с id={transportId}
    //ограничения: Только администраторы
    @GetMapping("/TransportHistory/{transportId}")
    public ResponseEntity<List<Rent>> getTransportHistoryById(@PathVariable String transportId) {
        return ResponseEntity.ok(rentService.adminGetTransportRentHistory(transportId));
    }

    //описание: Создание новой аренды
    //ограничения: Только администраторы
    @PostMapping("/Rent")
    public ResponseEntity<Rent> createRent(@RequestBody RentRequest request
    ) throws UserNotFoundException, TransportNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(rentService.createRent(request));

    }

    //описание: Завершение аренды транспорта по id аренды
    //ограничения: Только администраторы
    @PostMapping("/Rent/{rentId}")
    public ResponseEntity<Rent> endRent(@PathVariable String rentId,
                                        @RequestParam Double latitude,
                                        @RequestParam Double longitude
    ) throws UserNotFoundException, RentNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(rentService.adminEndRent(Long.parseLong(rentId), latitude, longitude));
    }

    //описание: Изменение записи об аренде по id
    //ограничения: Только администраторы
    @PutMapping("/Rent/{rentId}")
    public ResponseEntity<Rent> updateRentById(@PathVariable String rentId,
                                               @RequestBody RentRequest request
    ) throws UserNotFoundException, TransportNotFoundException, RentNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(rentService.updateRent(Long.parseLong(rentId), request));
    }

    //описание: Удаление информации об аренде по id
    //ограничения: Только администраторы
    @DeleteMapping("/Rent/{rentId}")
    public ResponseEntity<ApiResponse> deleteRentById(@PathVariable String rentId) {
        return ResponseEntity.ok(rentService.delete(Long.parseLong(rentId)));
    }

}
