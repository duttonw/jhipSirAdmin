package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.LocationEmail;
import au.gov.qld.sir.repository.LocationEmailRepository;
import au.gov.qld.sir.service.dto.LocationEmailDTO;
import au.gov.qld.sir.service.mapper.LocationEmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationEmail}.
 */
@Service
@Transactional
public class LocationEmailService {

    private final Logger log = LoggerFactory.getLogger(LocationEmailService.class);

    private final LocationEmailRepository locationEmailRepository;

    private final LocationEmailMapper locationEmailMapper;

    public LocationEmailService(LocationEmailRepository locationEmailRepository, LocationEmailMapper locationEmailMapper) {
        this.locationEmailRepository = locationEmailRepository;
        this.locationEmailMapper = locationEmailMapper;
    }

    /**
     * Save a locationEmail.
     *
     * @param locationEmailDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationEmailDTO save(LocationEmailDTO locationEmailDTO) {
        log.debug("Request to save LocationEmail : {}", locationEmailDTO);
        LocationEmail locationEmail = locationEmailMapper.toEntity(locationEmailDTO);
        locationEmail = locationEmailRepository.save(locationEmail);
        return locationEmailMapper.toDto(locationEmail);
    }

    /**
     * Get all the locationEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationEmails");
        return locationEmailRepository.findAll(pageable)
            .map(locationEmailMapper::toDto);
    }


    /**
     * Get one locationEmail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationEmailDTO> findOne(Long id) {
        log.debug("Request to get LocationEmail : {}", id);
        return locationEmailRepository.findById(id)
            .map(locationEmailMapper::toDto);
    }

    /**
     * Delete the locationEmail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationEmail : {}", id);
        locationEmailRepository.deleteById(id);
    }
}
