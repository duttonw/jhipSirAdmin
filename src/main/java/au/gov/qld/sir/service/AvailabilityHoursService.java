package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.repository.AvailabilityHoursRepository;
import au.gov.qld.sir.service.dto.AvailabilityHoursDTO;
import au.gov.qld.sir.service.mapper.AvailabilityHoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AvailabilityHours}.
 */
@Service
@Transactional
public class AvailabilityHoursService {

    private final Logger log = LoggerFactory.getLogger(AvailabilityHoursService.class);

    private final AvailabilityHoursRepository availabilityHoursRepository;

    private final AvailabilityHoursMapper availabilityHoursMapper;

    public AvailabilityHoursService(AvailabilityHoursRepository availabilityHoursRepository, AvailabilityHoursMapper availabilityHoursMapper) {
        this.availabilityHoursRepository = availabilityHoursRepository;
        this.availabilityHoursMapper = availabilityHoursMapper;
    }

    /**
     * Save a availabilityHours.
     *
     * @param availabilityHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public AvailabilityHoursDTO save(AvailabilityHoursDTO availabilityHoursDTO) {
        log.debug("Request to save AvailabilityHours : {}", availabilityHoursDTO);
        AvailabilityHours availabilityHours = availabilityHoursMapper.toEntity(availabilityHoursDTO);
        availabilityHours = availabilityHoursRepository.save(availabilityHours);
        return availabilityHoursMapper.toDto(availabilityHours);
    }

    /**
     * Get all the availabilityHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AvailabilityHoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvailabilityHours");
        return availabilityHoursRepository.findAll(pageable)
            .map(availabilityHoursMapper::toDto);
    }


    /**
     * Get one availabilityHours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvailabilityHoursDTO> findOne(Long id) {
        log.debug("Request to get AvailabilityHours : {}", id);
        return availabilityHoursRepository.findById(id)
            .map(availabilityHoursMapper::toDto);
    }

    /**
     * Delete the availabilityHours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AvailabilityHours : {}", id);
        availabilityHoursRepository.deleteById(id);
    }
}
