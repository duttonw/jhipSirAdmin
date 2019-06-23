package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceSupportRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceSupportRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSupportRoleRepository extends JpaRepository<ServiceSupportRole, Long>, JpaSpecificationExecutor<ServiceSupportRole> {

}
