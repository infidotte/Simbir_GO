package simbir.go.simbir_go.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import simbir.go.simbir_go.Utilit.TransportSerializer;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
@JsonSerialize(using = TransportSerializer.class)
public class Transport {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    private Account owner;

    private Boolean canBeRented;

    @OneToMany(targetEntity = Rent.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Rent> rent;

    private String transportType;
    private String model;
    private String color;
    private String identifier;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double minutePrice;
    private Double dayPrice;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transport transport = (Transport) o;
        return getId() != null && Objects.equals(getId(), transport.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
