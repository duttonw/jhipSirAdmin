package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceEvidence;
import au.gov.qld.sir.repository.ServiceEvidenceRepository;
import au.gov.qld.sir.service.dto.ServiceEvidenceDTO;
import au.gov.qld.sir.service.mapper.ServiceEvidenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceEvidence}.
 */
@Service
@Transactional
public class ServiceEvidenceService {

    private final Logger log = LoggerFactory.getLogger(ServiceEvidenceService.class);

    private final ServiceEvidenceRepository serviceEvidenceRepository;

    private final ServiceEvidenceMapper serviceEvidenceMapper;

    public ServiceEvidenceService(ServiceEvidenceRepository serviceEvidenceRepository, ServiceEvidenceMapper serviceEvidenceMapper) {
        this.serviceEvidenceRepository = serviceEvidenceRepository;
        this.serviceEvidenceMapper = serviceEvidenceMapper;
    }

    /**
     * Save a serviceEvidence.
     *
     * @param serviceEvidenceDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceEvidenceDTO save(ServiceEvidenceDTO serviceEvidenceDTO) {
        log.debug("Request to save ServiceEvidence : {}", serviceEvidenceDTO);
        ServiceEvidence serviceEvidence = serviceEvidenceMapper.toEntity(serviceEvidenceDTO);
        serviceEvidence = serviceEvidenceRepository.save(serviceEvidence);
        return serviceEvidenceMapper.toDto(serviceEvidence);
    }

    /**
     * Get all the serviceEvidences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEvidenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceEvidences");
        return serviceEvidenceRepository.findAll(pageable)
            .map(serviceEvidenceMapper::toDto);
    }


    /**
     * Get one serviceEvidence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceEvidenceDTO> findOne(Long id) {
        log.debug("Request to get ServiceEvidence : {}", id);
        return serviceEvidenceRepository.findById(id)
            .map(serviceEvidenceMapper::toDto);
    }

    /**
     * Delete the serviceEvidence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceEvidence : {}", id);
        serviceEvidenceRepository.deleteById(id);
    }
}
