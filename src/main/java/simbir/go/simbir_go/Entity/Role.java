package simbir.go.simbir_go.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Table
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(targetEntity = Account.class, mappedBy = "role")
    private Collection<Account> accounts;
}
