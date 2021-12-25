package judip.odm.repository;

import judip.odm.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepository extends JpaRepository<Value, Long> {
}
