package judip.odm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Attribute {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "attribute")
    private Set<Value> values;

}
