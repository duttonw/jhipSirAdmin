package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceDescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDescriptionRepository extends JpaRepository<ServiceDescription, Long>, JpaSpecificationExecutor<ServiceDescription> {

}
