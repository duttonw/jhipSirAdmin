package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceDeliveryForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceDeliveryForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDeliveryFormRepository extends JpaRepository<ServiceDeliveryForm, Long>, JpaSpecificationExecutor<ServiceDeliveryForm> {

}
