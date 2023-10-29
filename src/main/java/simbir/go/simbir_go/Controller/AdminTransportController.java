package simbir.go.simbir_go.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Transport;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.AdminTransportRequest;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.TransportRequest;
import simbir.go.simbir_go.Service.AccountService;
import simbir.go.simbir_go.Service.TransportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Transport")
public class AdminTransportController {
    private final TransportService transportService;
    private final AccountService accountService;

    //описание: Получение списка всех транспортных средств
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<Transport>> getAllTransport(@RequestParam Long start,
                                                           @RequestParam Integer count, @RequestParam String transportType
    ) {
        return ResponseEntity.ok(transportService.findAllAfterIdAndLessThenCount(start, count, transportType));
    }

    //описание: Получение информации о транспортном средстве по id
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable String id) throws TransportNotFoundException {
        return ResponseEntity.ok(transportService.getTransportById(Long.parseLong(id)));
    }

    //описание: Создание нового транспортного средства
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<Transport> createTransport(@Valid @RequestBody AdminTransportRequest request
    ) throws UserNotFoundException {
        return ResponseEntity.ok(transportService.createTransport(request, accountService.findById(request.getOwnerId())));
    }

    //описание: Изменение транспортного средства по id
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable String id, @Valid
    @RequestBody TransportRequest request
    ) throws TransportNotFoundException {
        return ResponseEntity.ok(transportService.updateTransport(Long.parseLong(id), request));
    }

    //описание: Удаление транспорта по id
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTransport(@PathVariable String id) throws TransportNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(transportService.deleteTransport(Long.parseLong(id)));
    }
}
