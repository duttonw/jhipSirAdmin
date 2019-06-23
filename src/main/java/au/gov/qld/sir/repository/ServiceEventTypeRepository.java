package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceEventType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceEventType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceEventTypeRepository extends JpaRepository<ServiceEventType, Long>, JpaSpecificationExecutor<ServiceEventType> {

}
