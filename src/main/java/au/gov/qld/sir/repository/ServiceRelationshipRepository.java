package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRelationshipRepository extends JpaRepository<ServiceRelationship, Long>, JpaSpecificationExecutor<ServiceRelationship> {

}
