package judip.odm.repository;

import judip.odm.model.OdmObject;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface OdmObjectRepository extends JpaRepository<OdmObject, Long> {

    List<OdmObject> findAllByParent(OdmObject parent);
}
