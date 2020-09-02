package guru.springframework.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER) // remove this FetchType and use org.springframework.orm.hibernate4.HibernateTransactionManager
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
