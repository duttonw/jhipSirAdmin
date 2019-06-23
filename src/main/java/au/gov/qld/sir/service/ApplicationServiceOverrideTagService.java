package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationServiceOverrideTag}.
 */
@Service
@Transactional
public class ApplicationServiceOverrideTagService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagService.class);

    private final ApplicationServiceOverrideTagRepository applicationServiceOverrideTagRepository;

    private final ApplicationServiceOverrideTagMapper applicationServiceOverrideTagMapper;

    public ApplicationServiceOverrideTagService(ApplicationServiceOverrideTagRepository applicationServiceOverrideTagRepository, ApplicationServiceOverrideTagMapper applicationServiceOverrideTagMapper) {
        this.applicationServiceOverrideTagRepository = applicationServiceOverrideTagRepository;
        this.applicationServiceOverrideTagMapper = applicationServiceOverrideTagMapper;
    }

    /**
     * Save a applicationServiceOverrideTag.
     *
     * @param applicationServiceOverrideTagDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationServiceOverrideTagDTO save(ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO) {
        log.debug("Request to save ApplicationServiceOverrideTag : {}", applicationServiceOverrideTagDTO);
        ApplicationServiceOverrideTag applicationServiceOverrideTag = applicationServiceOverrideTagMapper.toEntity(applicationServiceOverrideTagDTO);
        applicationServiceOverrideTag = applicationServiceOverrideTagRepository.save(applicationServiceOverrideTag);
        return applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTag);
    }

    /**
     * Get all the applicationServiceOverrideTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationServiceOverrideTags");
        return applicationServiceOverrideTagRepository.findAll(pageable)
            .map(applicationServiceOverrideTagMapper::toDto);
    }


    /**
     * Get one applicationServiceOverrideTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationServiceOverrideTagDTO> findOne(Long id) {
        log.debug("Request to get ApplicationServiceOverrideTag : {}", id);
        return applicationServiceOverrideTagRepository.findById(id)
            .map(applicationServiceOverrideTagMapper::toDto);
    }

    /**
     * Delete the applicationServiceOverrideTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationServiceOverrideTag : {}", id);
        applicationServiceOverrideTagRepository.deleteById(id);
    }
}
