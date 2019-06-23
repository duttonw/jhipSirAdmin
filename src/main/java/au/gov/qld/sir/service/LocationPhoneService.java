package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.LocationPhone;
import au.gov.qld.sir.repository.LocationPhoneRepository;
import au.gov.qld.sir.service.dto.LocationPhoneDTO;
import au.gov.qld.sir.service.mapper.LocationPhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationPhone}.
 */
@Service
@Transactional
public class LocationPhoneService {

    private final Logger log = LoggerFactory.getLogger(LocationPhoneService.class);

    private final LocationPhoneRepository locationPhoneRepository;

    private final LocationPhoneMapper locationPhoneMapper;

    public LocationPhoneService(LocationPhoneRepository locationPhoneRepository, LocationPhoneMapper locationPhoneMapper) {
        this.locationPhoneRepository = locationPhoneRepository;
        this.locationPhoneMapper = locationPhoneMapper;
    }

    /**
     * Save a locationPhone.
     *
     * @param locationPhoneDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationPhoneDTO save(LocationPhoneDTO locationPhoneDTO) {
        log.debug("Request to save LocationPhone : {}", locationPhoneDTO);
        LocationPhone locationPhone = locationPhoneMapper.toEntity(locationPhoneDTO);
        locationPhone = locationPhoneRepository.save(locationPhone);
        return locationPhoneMapper.toDto(locationPhone);
    }

    /**
     * Get all the locationPhones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationPhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationPhones");
        return locationPhoneRepository.findAll(pageable)
            .map(locationPhoneMapper::toDto);
    }


    /**
     * Get one locationPhone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationPhoneDTO> findOne(Long id) {
        log.debug("Request to get LocationPhone : {}", id);
        return locationPhoneRepository.findById(id)
            .map(locationPhoneMapper::toDto);
    }

    /**
     * Delete the locationPhone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationPhone : {}", id);
        locationPhoneRepository.deleteById(id);
    }
}
