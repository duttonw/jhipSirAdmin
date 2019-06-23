package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.LocationAddress;
import au.gov.qld.sir.repository.LocationAddressRepository;
import au.gov.qld.sir.service.dto.LocationAddressDTO;
import au.gov.qld.sir.service.mapper.LocationAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationAddress}.
 */
@Service
@Transactional
public class LocationAddressService {

    private final Logger log = LoggerFactory.getLogger(LocationAddressService.class);

    private final LocationAddressRepository locationAddressRepository;

    private final LocationAddressMapper locationAddressMapper;

    public LocationAddressService(LocationAddressRepository locationAddressRepository, LocationAddressMapper locationAddressMapper) {
        this.locationAddressRepository = locationAddressRepository;
        this.locationAddressMapper = locationAddressMapper;
    }

    /**
     * Save a locationAddress.
     *
     * @param locationAddressDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationAddressDTO save(LocationAddressDTO locationAddressDTO) {
        log.debug("Request to save LocationAddress : {}", locationAddressDTO);
        LocationAddress locationAddress = locationAddressMapper.toEntity(locationAddressDTO);
        locationAddress = locationAddressRepository.save(locationAddress);
        return locationAddressMapper.toDto(locationAddress);
    }

    /**
     * Get all the locationAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationAddresses");
        return locationAddressRepository.findAll(pageable)
            .map(locationAddressMapper::toDto);
    }


    /**
     * Get one locationAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationAddressDTO> findOne(Long id) {
        log.debug("Request to get LocationAddress : {}", id);
        return locationAddressRepository.findById(id)
            .map(locationAddressMapper::toDto);
    }

    /**
     * Delete the locationAddress by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationAddress : {}", id);
        locationAddressRepository.deleteById(id);
    }
}
