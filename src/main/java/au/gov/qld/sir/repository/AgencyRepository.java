package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.Agency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Agency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long>, JpaSpecificationExecutor<Agency> {

}
