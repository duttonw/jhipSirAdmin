package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long>, JpaSpecificationExecutor<ServiceRecord> {

}
