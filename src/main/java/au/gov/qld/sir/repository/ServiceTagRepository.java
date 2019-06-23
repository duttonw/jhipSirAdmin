package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceTag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTagRepository extends JpaRepository<ServiceTag, Long>, JpaSpecificationExecutor<ServiceTag> {

}
