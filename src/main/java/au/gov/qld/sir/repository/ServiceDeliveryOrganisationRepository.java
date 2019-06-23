package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceDeliveryOrganisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDeliveryOrganisationRepository extends JpaRepository<ServiceDeliveryOrganisation, Long>, JpaSpecificationExecutor<ServiceDeliveryOrganisation> {

}
