package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceNameRepository extends JpaRepository<ServiceName, Long>, JpaSpecificationExecutor<ServiceName> {

}
