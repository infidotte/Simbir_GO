package simbir.go.simbir_go.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Role;
import simbir.go.simbir_go.Exception.UserAlreadyExistsException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.AdminAuthRequest;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Repository.AccountRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final RoleService roleService;

    public Account findByUsername(String username) throws UserNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public Account findUserDetailsByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }


    public Account save(Account account) throws UserAlreadyExistsException {
        if (!repository.existsByUsername(account.getUsername())) {
            return repository.save(account);
        } else {
            throw new UserAlreadyExistsException(account.getUsername());
        }
    }

    public void updateUsernameAndPasswordById(String username, String password, Long id) {
        repository.updateUsernameAndPasswordById(username, password, id);
    }

    public void updateBalanceById(Double balance, Long id) {
        repository.updateBalanceById(balance, id);
    }

    public Account findById(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("id (" + id + ")"));
    }

    public List<Account> findCount(Long start, Integer count) {
        return repository.findByIdGreaterThanEqual(start).stream().limit(count).sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());
    }


    public ApiResponse adminUpdate(Long id, AdminAuthRequest request) throws UserAlreadyExistsException {
        Role role = request.getIsAdmin() ? roleService.findByName("ROLE_ADMIN") : roleService.findByName("ROLE_USER");
        repository.updateUsernameAndPasswordAndRoleAndBalanceById(request.getUsername(), request.getPassword(), role, request.getBalance(), id);
        return new ApiResponse("message", "User was updated");
    }

    public ApiResponse delete(Long id) {
        repository.deleteById(id);
        return new ApiResponse("message", "User was deleted");
    }
}


