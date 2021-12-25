package judip.odm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Value {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Attribute attribute;
    @ManyToOne
    private OdmObject odmObject;
    private String value;
}
