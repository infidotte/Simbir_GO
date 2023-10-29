package simbir.go.simbir_go.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Exception.UserAlreadyExistsException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.AdminAuthRequest;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Service.AccountService;
import simbir.go.simbir_go.Service.AuthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Account")
public class AdminAccountController {
    private final AccountService accountService;
    private final AuthService authService;

    //описание: Получение списка всех аккаунтов
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam Long start,
                                                        @RequestParam Integer count) {
        return ResponseEntity.ok(accountService.findCount(start, count));
    }

    //описание: Получение информации об аккаунте по id
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) throws UserNotFoundException {
        return ResponseEntity.ok(accountService.findById(Long.parseLong(id)));
    }

    //описание: Создание администратором нового аккаунта
    //ограничения: Только администраторы, нельзя создать аккаунт с уже существующим в системе username
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AdminAuthRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authService.adminCreate(request));
    }

    //описание: Изменение администратором аккаунта по id
    //ограничения: Только администраторы, нельзя изменять username на уже существующий в системе
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable String id,
                                                     @RequestBody AdminAuthRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(accountService.adminUpdate(Long.parseLong(id), request));
    }

    //описание: Удаление аккаунта по id
    //ограничения: Только администраторы
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable String id) {
        return ResponseEntity.ok(accountService.delete(Long.parseLong(id)));
    }
}
