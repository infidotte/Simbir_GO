package simbir.go.simbir_go.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Transport;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.TransportRequest;
import simbir.go.simbir_go.Service.AuthService;
import simbir.go.simbir_go.Service.TransportService;

@RestController
@RequestMapping("/api/Transport")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;
    private final AuthService authService;

    //описание: Получение информации о транспорте по id
    //ограничения: нет
    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable String id) throws TransportNotFoundException {
        return ResponseEntity.ok(transportService.getTransportById(Long.parseLong(id)));
    }

    //описание: Добавление нового транспорта
    //ограничения: Только авторизованные пользователи
    @PostMapping
    public ResponseEntity<Transport> createTransport(@Valid @RequestBody TransportRequest request) {
        return ResponseEntity.ok(transportService.createTransport(request, authService.me()));
    }

    //описание: Изменение транспорта оп id
    //ограничения: Только владелец этого транспорта
    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable String id, @Valid
    @RequestBody TransportRequest request
    ) throws TransportNotFoundException {
        return ResponseEntity.ok(transportService.updateTransport(Long.parseLong(id), request));
    }

    //описание: Удаление транспорта по id
    //ограничения: Только владелец этого транспорта
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTransport(@PathVariable String id) throws TransportNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(transportService.deleteTransport(Long.parseLong(id)));
    }
}
