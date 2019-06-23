package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.AgencyType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgencyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyTypeRepository extends JpaRepository<AgencyType, Long>, JpaSpecificationExecutor<AgencyType> {

}
