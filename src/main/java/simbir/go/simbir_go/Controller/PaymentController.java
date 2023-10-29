package simbir.go.simbir_go.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Service.AccountService;
import simbir.go.simbir_go.Service.AuthService;
import simbir.go.simbir_go.Service.PaymentService;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    //описание: Добавляет на баланс аккаунта с id={accountId} 250 000 денежных единиц.
    //ограничения: Администратор может добавить баланс всем, обычный пользователь
    //только себе
    @PostMapping("/api/Payment/Hesoyam/{accountId}")
    public ResponseEntity<ApiResponse> addMoney(@PathVariable String accountId) throws UserNotFoundException, MethodNotAllowedException {
        return ResponseEntity.ok(paymentService.addMoney(Long.parseLong(accountId)));
    }
}
