package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ApplicationServiceOverride;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationServiceOverride entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationServiceOverrideRepository extends JpaRepository<ApplicationServiceOverride, Long>, JpaSpecificationExecutor<ApplicationServiceOverride> {

}
