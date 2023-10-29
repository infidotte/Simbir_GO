package simbir.go.simbir_go.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import simbir.go.simbir_go.Utilit.RentSerializer;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
@JsonSerialize(using = RentSerializer.class)
public class Rent {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Transport.class)
    private Transport transport;

    @ManyToOne(targetEntity = Account.class)
    private Account user;

    private String timeStart;
    private String timeEnd;
    private Double priceOfUnit;
    private String priceType; //Minutes, Days
    private Double finalPrice;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Rent rent = (Rent) o;
        return getId() != null && Objects.equals(getId(), rent.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
