package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.IntegrationMapping;
import au.gov.qld.sir.repository.IntegrationMappingRepository;
import au.gov.qld.sir.service.dto.IntegrationMappingDTO;
import au.gov.qld.sir.service.mapper.IntegrationMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IntegrationMapping}.
 */
@Service
@Transactional
public class IntegrationMappingService {

    private final Logger log = LoggerFactory.getLogger(IntegrationMappingService.class);

    private final IntegrationMappingRepository integrationMappingRepository;

    private final IntegrationMappingMapper integrationMappingMapper;

    public IntegrationMappingService(IntegrationMappingRepository integrationMappingRepository, IntegrationMappingMapper integrationMappingMapper) {
        this.integrationMappingRepository = integrationMappingRepository;
        this.integrationMappingMapper = integrationMappingMapper;
    }

    /**
     * Save a integrationMapping.
     *
     * @param integrationMappingDTO the entity to save.
     * @return the persisted entity.
     */
    public IntegrationMappingDTO save(IntegrationMappingDTO integrationMappingDTO) {
        log.debug("Request to save IntegrationMapping : {}", integrationMappingDTO);
        IntegrationMapping integrationMapping = integrationMappingMapper.toEntity(integrationMappingDTO);
        integrationMapping = integrationMappingRepository.save(integrationMapping);
        return integrationMappingMapper.toDto(integrationMapping);
    }

    /**
     * Get all the integrationMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IntegrationMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntegrationMappings");
        return integrationMappingRepository.findAll(pageable)
            .map(integrationMappingMapper::toDto);
    }


    /**
     * Get one integrationMapping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IntegrationMappingDTO> findOne(Long id) {
        log.debug("Request to get IntegrationMapping : {}", id);
        return integrationMappingRepository.findById(id)
            .map(integrationMappingMapper::toDto);
    }

    /**
     * Delete the integrationMapping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IntegrationMapping : {}", id);
        integrationMappingRepository.deleteById(id);
    }
}
