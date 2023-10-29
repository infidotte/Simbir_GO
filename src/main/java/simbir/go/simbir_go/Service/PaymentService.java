package simbir.go.simbir_go.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final AuthService authService;
    private final AccountService accountService;
    public ApiResponse addMoney(Long accountId) throws UserNotFoundException, MethodNotAllowedException {
        Account current = authService.me();
        if(current.getId().equals(accountId)){
            Double currBalance = current.getBalance();
            accountService.updateBalanceById(currBalance + 250000.0, current.getId());
            return new ApiResponse("message", "Balance was updated");
        }
        if(current.getRole().getName().equals("ROLE_ADMIN")){
            current = accountService.findById(accountId);
            Double currBalance = current.getBalance();
            accountService.updateBalanceById(currBalance + 250000.0, current.getId());
            return new ApiResponse("message", "Balance was updated");
        }else {
            throw new MethodNotAllowedException("It is not your id");
        }
    }

    public void payTo(Long idFrom, Long idTo, Double finalPrice) throws UserNotFoundException {
        Account account = accountService.findById(idFrom);
        Double currBalance = account.getBalance();
        accountService.updateBalanceById(currBalance-finalPrice, idFrom);
        account = accountService.findById(idTo);
        currBalance = account.getBalance();
        accountService.updateBalanceById(currBalance+finalPrice, idTo);
    }
}
