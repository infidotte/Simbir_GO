package simbir.go.simbir_go.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Role;
import simbir.go.simbir_go.Exception.UserAlreadyExistsException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.AdminAuthRequest;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.AuthRequest;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Account me() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public ApiResponse signUp(AuthRequest request) throws UserAlreadyExistsException {
        Account user = new Account();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setBalance(0.0);
        accountService.save(user);
        return new ApiResponse("message", "User was created");
    }

    public ApiResponse signIn(AuthRequest request) throws UserNotFoundException, AuthenticationException {
        Account user = accountService.findByUsername(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        String jwtToken = jwtService.generateToken(user);
        return new ApiResponse("token", jwtToken);
    }

    public ApiResponse update(AuthRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.updateUsernameAndPasswordById(request.getUsername(), request.getPassword(), account.getId());
        return new ApiResponse("Message", "User was updated");
    }

    public Account adminCreate(AdminAuthRequest request) throws UserAlreadyExistsException {
        Role role = request.getIsAdmin() ? roleService.findByName("ROLE_ADMIN") : roleService.findByName("ROLE_USER");
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(role);
        account.setBalance(request.getBalance());
        return accountService.save(account);
    }
}
