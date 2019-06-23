package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.LocationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Long>, JpaSpecificationExecutor<LocationType> {

}
