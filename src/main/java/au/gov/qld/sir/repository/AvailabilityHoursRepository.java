package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.AvailabilityHours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AvailabilityHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvailabilityHoursRepository extends JpaRepository<AvailabilityHours, Long>, JpaSpecificationExecutor<AvailabilityHours> {

}
