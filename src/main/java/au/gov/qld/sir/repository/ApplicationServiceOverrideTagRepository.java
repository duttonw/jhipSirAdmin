package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationServiceOverrideTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationServiceOverrideTagRepository extends JpaRepository<ApplicationServiceOverrideTag, Long>, JpaSpecificationExecutor<ApplicationServiceOverrideTag> {

}
