package au.gov.qld.sir.repository;

import au.gov.qld.sir.domain.CategoryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long>, JpaSpecificationExecutor<CategoryType> {

}
