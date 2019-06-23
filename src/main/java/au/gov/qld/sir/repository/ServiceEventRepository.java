package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceEventRepository extends JpaRepository<ServiceEvent, Long>, JpaSpecificationExecutor<ServiceEvent> {

}
