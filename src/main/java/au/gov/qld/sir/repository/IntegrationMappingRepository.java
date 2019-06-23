package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.IntegrationMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IntegrationMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrationMappingRepository extends JpaRepository<IntegrationMapping, Long>, JpaSpecificationExecutor<IntegrationMapping> {

}
