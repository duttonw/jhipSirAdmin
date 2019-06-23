package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceRoleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceRoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRoleTypeRepository extends JpaRepository<ServiceRoleType, Long>, JpaSpecificationExecutor<ServiceRoleType> {

}
