package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceFranchise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceFranchise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceFranchiseRepository extends JpaRepository<ServiceFranchise, Long>, JpaSpecificationExecutor<ServiceFranchise> {

}
