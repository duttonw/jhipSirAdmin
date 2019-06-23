package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.ServiceTagItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceTagItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTagItemsRepository extends JpaRepository<ServiceTagItems, Long>, JpaSpecificationExecutor<ServiceTagItems> {

}
