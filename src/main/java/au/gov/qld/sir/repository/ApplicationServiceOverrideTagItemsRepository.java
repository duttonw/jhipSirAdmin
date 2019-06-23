package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationServiceOverrideTagItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationServiceOverrideTagItemsRepository extends JpaRepository<ApplicationServiceOverrideTagItems, Long>, JpaSpecificationExecutor<ApplicationServiceOverrideTagItems> {

}
