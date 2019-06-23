package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationServiceOverrideAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationServiceOverrideAttributeRepository extends JpaRepository<ApplicationServiceOverrideAttribute, Long>, JpaSpecificationExecutor<ApplicationServiceOverrideAttribute> {

}
