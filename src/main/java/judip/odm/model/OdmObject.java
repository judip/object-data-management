package judip.odm.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class OdmObject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationDate;
    private String identifier;
    @ManyToOne
    private OdmObjectType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private OdmObject parent;

    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<OdmObject> children = new HashSet<>();

    @OneToMany(mappedBy = "odmObject")
    private Set<Value> values;

}
