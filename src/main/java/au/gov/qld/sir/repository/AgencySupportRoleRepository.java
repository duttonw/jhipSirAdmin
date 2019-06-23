package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.AgencySupportRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgencySupportRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencySupportRoleRepository extends JpaRepository<AgencySupportRole, Long>, JpaSpecificationExecutor<AgencySupportRole> {

}
