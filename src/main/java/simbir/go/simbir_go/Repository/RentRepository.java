package simbir.go.simbir_go.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Rent;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByTransport_Id(Long id);

    List<Rent> findByUser(Account user);

    @Query("select r from Rent r where r.user.id = ?1")
    List<Rent> findByUser_Id(Long id);




}
