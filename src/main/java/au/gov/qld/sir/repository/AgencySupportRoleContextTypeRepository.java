package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.AgencySupportRoleContextType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgencySupportRoleContextType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencySupportRoleContextTypeRepository extends JpaRepository<AgencySupportRoleContextType, Long>, JpaSpecificationExecutor<AgencySupportRoleContextType> {

}
