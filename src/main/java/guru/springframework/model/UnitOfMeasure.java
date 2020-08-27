package guru.springframework.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uom;

}
