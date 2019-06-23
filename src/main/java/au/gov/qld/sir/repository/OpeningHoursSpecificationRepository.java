package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.OpeningHoursSpecification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OpeningHoursSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpeningHoursSpecificationRepository extends JpaRepository<OpeningHoursSpecification, Long>, JpaSpecificationExecutor<OpeningHoursSpecification> {

}
