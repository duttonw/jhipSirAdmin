package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {


    @Mapping(target = "serviceEvidences", ignore = true)
    @Mapping(target = "removeServiceEvidence", ignore = true)
    @Mapping(target = "serviceGroups", ignore = true)
    @Mapping(target = "removeServiceGroup", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
