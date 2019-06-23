package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.DeliveryChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryChannelRepository extends JpaRepository<DeliveryChannel, Long>, JpaSpecificationExecutor<DeliveryChannel> {

}
