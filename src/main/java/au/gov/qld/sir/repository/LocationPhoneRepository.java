package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.LocationPhone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocationPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationPhoneRepository extends JpaRepository<LocationPhone, Long>, JpaSpecificationExecutor<LocationPhone> {

}
