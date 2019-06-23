package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.Application;
import au.gov.qld.sir.repository.ApplicationRepository;
import au.gov.qld.sir.service.dto.ApplicationDTO;
import au.gov.qld.sir.service.mapper.ApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Application}.
 */
@Service
@Transactional
public class ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationDTO save(ApplicationDTO applicationDTO) {
        log.debug("Request to save Application : {}", applicationDTO);
        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll(pageable)
            .map(applicationMapper::toDto);
    }


    /**
     * Get one application by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return applicationRepository.findById(id)
            .map(applicationMapper::toDto);
    }

    /**
     * Delete the application by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.deleteById(id);
    }
}
