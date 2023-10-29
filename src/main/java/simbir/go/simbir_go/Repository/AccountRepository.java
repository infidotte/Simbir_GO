package simbir.go.simbir_go.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    @Modifying
    @Query("update Account a set a.balance = ?1 where a.id = ?2")
    void updateBalanceById(Double balance, Long id);
    @Transactional
    @Modifying
    @Query("update Account a set a.username = ?1, a.password = ?2 where a.id = ?3")
    void updateUsernameAndPasswordById(String username, String password, Long id);

    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<Account> findByIdGreaterThanEqual(Long id);

    @Transactional
    @Modifying
    @Query("update Account a set a.username = ?1, a.password = ?2, a.role = ?3, a.balance = ?4 where a.id = ?5")
    void updateUsernameAndPasswordAndRoleAndBalanceById(String username, String password, Role role, Double balance, Long id);



}
