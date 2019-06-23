package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.CategoryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryType} and its DTO {@link CategoryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryTypeMapper extends EntityMapper<CategoryTypeDTO, CategoryType> {


    @Mapping(target = "serviceGroups", ignore = true)
    @Mapping(target = "removeServiceGroup", ignore = true)
    CategoryType toEntity(CategoryTypeDTO categoryTypeDTO);

    default CategoryType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryType categoryType = new CategoryType();
        categoryType.setId(id);
        return categoryType;
    }
}
