package simbir.go.simbir_go.Repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Transport;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    @Query("select t from Transport t where t.id > ?1 and t.transportType = ?2")
    List<Transport> findByIdAfterAndTransportType(Long id, String transportType);

    @Query("select t from Transport t where t.id > ?1")
    List<Transport> findByIdAfter(Long id);


    @Override
    void deleteById(@NonNull Long aLong);
}
