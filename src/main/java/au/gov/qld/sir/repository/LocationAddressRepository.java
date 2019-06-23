package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.LocationAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocationAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationAddressRepository extends JpaRepository<LocationAddress, Long>, JpaSpecificationExecutor<LocationAddress> {

}
