package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceEvidence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceEvidence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceEvidenceRepository extends JpaRepository<ServiceEvidence, Long>, JpaSpecificationExecutor<ServiceEvidence> {

}
