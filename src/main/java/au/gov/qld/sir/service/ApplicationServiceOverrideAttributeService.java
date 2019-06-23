package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute;
import au.gov.qld.sir.repository.ApplicationServiceOverrideAttributeRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationServiceOverrideAttribute}.
 */
@Service
@Transactional
public class ApplicationServiceOverrideAttributeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideAttributeService.class);

    private final ApplicationServiceOverrideAttributeRepository applicationServiceOverrideAttributeRepository;

    private final ApplicationServiceOverrideAttributeMapper applicationServiceOverrideAttributeMapper;

    public ApplicationServiceOverrideAttributeService(ApplicationServiceOverrideAttributeRepository applicationServiceOverrideAttributeRepository, ApplicationServiceOverrideAttributeMapper applicationServiceOverrideAttributeMapper) {
        this.applicationServiceOverrideAttributeRepository = applicationServiceOverrideAttributeRepository;
        this.applicationServiceOverrideAttributeMapper = applicationServiceOverrideAttributeMapper;
    }

    /**
     * Save a applicationServiceOverrideAttribute.
     *
     * @param applicationServiceOverrideAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationServiceOverrideAttributeDTO save(ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO) {
        log.debug("Request to save ApplicationServiceOverrideAttribute : {}", applicationServiceOverrideAttributeDTO);
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute = applicationServiceOverrideAttributeMapper.toEntity(applicationServiceOverrideAttributeDTO);
        applicationServiceOverrideAttribute = applicationServiceOverrideAttributeRepository.save(applicationServiceOverrideAttribute);
        return applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttribute);
    }

    /**
     * Get all the applicationServiceOverrideAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideAttributeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationServiceOverrideAttributes");
        return applicationServiceOverrideAttributeRepository.findAll(pageable)
            .map(applicationServiceOverrideAttributeMapper::toDto);
    }


    /**
     * Get one applicationServiceOverrideAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationServiceOverrideAttributeDTO> findOne(Long id) {
        log.debug("Request to get ApplicationServiceOverrideAttribute : {}", id);
        return applicationServiceOverrideAttributeRepository.findById(id)
            .map(applicationServiceOverrideAttributeMapper::toDto);
    }

    /**
     * Delete the applicationServiceOverrideAttribute by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationServiceOverrideAttribute : {}", id);
        applicationServiceOverrideAttributeRepository.deleteById(id);
    }
}
