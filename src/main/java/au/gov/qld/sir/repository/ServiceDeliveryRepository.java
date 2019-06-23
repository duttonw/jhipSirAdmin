package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceDelivery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceDelivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDeliveryRepository extends JpaRepository<ServiceDelivery, Long>, JpaSpecificationExecutor<ServiceDelivery> {

}
