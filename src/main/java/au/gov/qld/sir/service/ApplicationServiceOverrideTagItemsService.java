package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagItemsRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationServiceOverrideTagItems}.
 */
@Service
@Transactional
public class ApplicationServiceOverrideTagItemsService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagItemsService.class);

    private final ApplicationServiceOverrideTagItemsRepository applicationServiceOverrideTagItemsRepository;

    private final ApplicationServiceOverrideTagItemsMapper applicationServiceOverrideTagItemsMapper;

    public ApplicationServiceOverrideTagItemsService(ApplicationServiceOverrideTagItemsRepository applicationServiceOverrideTagItemsRepository, ApplicationServiceOverrideTagItemsMapper applicationServiceOverrideTagItemsMapper) {
        this.applicationServiceOverrideTagItemsRepository = applicationServiceOverrideTagItemsRepository;
        this.applicationServiceOverrideTagItemsMapper = applicationServiceOverrideTagItemsMapper;
    }

    /**
     * Save a applicationServiceOverrideTagItems.
     *
     * @param applicationServiceOverrideTagItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationServiceOverrideTagItemsDTO save(ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO) {
        log.debug("Request to save ApplicationServiceOverrideTagItems : {}", applicationServiceOverrideTagItemsDTO);
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = applicationServiceOverrideTagItemsMapper.toEntity(applicationServiceOverrideTagItemsDTO);
        applicationServiceOverrideTagItems = applicationServiceOverrideTagItemsRepository.save(applicationServiceOverrideTagItems);
        return applicationServiceOverrideTagItemsMapper.toDto(applicationServiceOverrideTagItems);
    }

    /**
     * Get all the applicationServiceOverrideTagItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideTagItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationServiceOverrideTagItems");
        return applicationServiceOverrideTagItemsRepository.findAll(pageable)
            .map(applicationServiceOverrideTagItemsMapper::toDto);
    }


    /**
     * Get one applicationServiceOverrideTagItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationServiceOverrideTagItemsDTO> findOne(Long id) {
        log.debug("Request to get ApplicationServiceOverrideTagItems : {}", id);
        return applicationServiceOverrideTagItemsRepository.findById(id)
            .map(applicationServiceOverrideTagItemsMapper::toDto);
    }

    /**
     * Delete the applicationServiceOverrideTagItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationServiceOverrideTagItems : {}", id);
        applicationServiceOverrideTagItemsRepository.deleteById(id);
    }
}
