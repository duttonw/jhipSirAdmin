package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.repository.ApplicationServiceOverrideRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationServiceOverride}.
 */
@Service
@Transactional
public class ApplicationServiceOverrideService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideService.class);

    private final ApplicationServiceOverrideRepository applicationServiceOverrideRepository;

    private final ApplicationServiceOverrideMapper applicationServiceOverrideMapper;

    public ApplicationServiceOverrideService(ApplicationServiceOverrideRepository applicationServiceOverrideRepository, ApplicationServiceOverrideMapper applicationServiceOverrideMapper) {
        this.applicationServiceOverrideRepository = applicationServiceOverrideRepository;
        this.applicationServiceOverrideMapper = applicationServiceOverrideMapper;
    }

    /**
     * Save a applicationServiceOverride.
     *
     * @param applicationServiceOverrideDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationServiceOverrideDTO save(ApplicationServiceOverrideDTO applicationServiceOverrideDTO) {
        log.debug("Request to save ApplicationServiceOverride : {}", applicationServiceOverrideDTO);
        ApplicationServiceOverride applicationServiceOverride = applicationServiceOverrideMapper.toEntity(applicationServiceOverrideDTO);
        applicationServiceOverride = applicationServiceOverrideRepository.save(applicationServiceOverride);
        return applicationServiceOverrideMapper.toDto(applicationServiceOverride);
    }

    /**
     * Get all the applicationServiceOverrides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationServiceOverrides");
        return applicationServiceOverrideRepository.findAll(pageable)
            .map(applicationServiceOverrideMapper::toDto);
    }


    /**
     * Get one applicationServiceOverride by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationServiceOverrideDTO> findOne(Long id) {
        log.debug("Request to get ApplicationServiceOverride : {}", id);
        return applicationServiceOverrideRepository.findById(id)
            .map(applicationServiceOverrideMapper::toDto);
    }

    /**
     * Delete the applicationServiceOverride by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationServiceOverride : {}", id);
        applicationServiceOverrideRepository.deleteById(id);
    }
}
