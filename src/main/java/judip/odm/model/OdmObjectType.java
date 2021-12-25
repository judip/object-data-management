package judip.odm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
public class OdmObjectType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<OdmObject> odmObjects;
}
