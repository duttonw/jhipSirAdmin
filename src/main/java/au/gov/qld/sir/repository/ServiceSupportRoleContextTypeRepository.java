package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceSupportRoleContextType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceSupportRoleContextType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSupportRoleContextTypeRepository extends JpaRepository<ServiceSupportRoleContextType, Long>, JpaSpecificationExecutor<ServiceSupportRoleContextType> {

}
