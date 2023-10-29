package simbir.go.simbir_go.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Exception.UserAlreadyExistsException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.AuthRequest;
import simbir.go.simbir_go.Service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Account")
public class AccountController {

    private final AuthService authService;

    //описание: получение данных о текущем аккаунте
    //ограничения: только авторизованные пользователи
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/Me")
    public ResponseEntity<Account> getCurrentUser() {
        return ResponseEntity.ok(authService.me());
    }

    //описание: получение нового jwt токена пользователя
    //ограничения: нет
    @PostMapping("/SignIn")
    public ResponseEntity<ApiResponse> signIn(@RequestBody AuthRequest request) throws UserNotFoundException, AuthenticationException {
        return ResponseEntity.ok(authService.signIn(request));
    }

    //описание: регистрация нового аккаунта
    //ограничения: нельзя создать пользователя с существующим username
    @PostMapping("/SignUp")
    public ResponseEntity<ApiResponse> signUp(@RequestBody AuthRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authService.signUp(request));
    }

    //описание: выход из аккаунта
    // ограничения: только авторизованные пользователи
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/SignOut")
    public ResponseEntity<ApiResponse> signOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new ApiResponse("message", "Successfully log out"));
    }

    //описание: обновление своего аккаунта
    //ограничения: только авторизованные пользователи, нельзя использовать уже
    //используемые в системе username
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/Update")
    public ResponseEntity<ApiResponse> update(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.update(request));
    }
}
