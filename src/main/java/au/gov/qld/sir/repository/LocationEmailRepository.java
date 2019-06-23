package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.LocationEmail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocationEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationEmailRepository extends JpaRepository<LocationEmail, Long>, JpaSpecificationExecutor<LocationEmail> {

}
