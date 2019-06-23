package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.LocationType;
import au.gov.qld.sir.repository.LocationTypeRepository;
import au.gov.qld.sir.service.dto.LocationTypeDTO;
import au.gov.qld.sir.service.mapper.LocationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationType}.
 */
@Service
@Transactional
public class LocationTypeService {

    private final Logger log = LoggerFactory.getLogger(LocationTypeService.class);

    private final LocationTypeRepository locationTypeRepository;

    private final LocationTypeMapper locationTypeMapper;

    public LocationTypeService(LocationTypeRepository locationTypeRepository, LocationTypeMapper locationTypeMapper) {
        this.locationTypeRepository = locationTypeRepository;
        this.locationTypeMapper = locationTypeMapper;
    }

    /**
     * Save a locationType.
     *
     * @param locationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationTypeDTO save(LocationTypeDTO locationTypeDTO) {
        log.debug("Request to save LocationType : {}", locationTypeDTO);
        LocationType locationType = locationTypeMapper.toEntity(locationTypeDTO);
        locationType = locationTypeRepository.save(locationType);
        return locationTypeMapper.toDto(locationType);
    }

    /**
     * Get all the locationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationTypes");
        return locationTypeRepository.findAll(pageable)
            .map(locationTypeMapper::toDto);
    }


    /**
     * Get one locationType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationTypeDTO> findOne(Long id) {
        log.debug("Request to get LocationType : {}", id);
        return locationTypeRepository.findById(id)
            .map(locationTypeMapper::toDto);
    }

    /**
     * Delete the locationType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationType : {}", id);
        locationTypeRepository.deleteById(id);
    }
}
