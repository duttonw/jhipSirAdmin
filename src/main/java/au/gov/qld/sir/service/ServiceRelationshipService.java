package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceRelationship;
import au.gov.qld.sir.repository.ServiceRelationshipRepository;
import au.gov.qld.sir.service.dto.ServiceRelationshipDTO;
import au.gov.qld.sir.service.mapper.ServiceRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceRelationship}.
 */
@Service
@Transactional
public class ServiceRelationshipService {

    private final Logger log = LoggerFactory.getLogger(ServiceRelationshipService.class);

    private final ServiceRelationshipRepository serviceRelationshipRepository;

    private final ServiceRelationshipMapper serviceRelationshipMapper;

    public ServiceRelationshipService(ServiceRelationshipRepository serviceRelationshipRepository, ServiceRelationshipMapper serviceRelationshipMapper) {
        this.serviceRelationshipRepository = serviceRelationshipRepository;
        this.serviceRelationshipMapper = serviceRelationshipMapper;
    }

    /**
     * Save a serviceRelationship.
     *
     * @param serviceRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRelationshipDTO save(ServiceRelationshipDTO serviceRelationshipDTO) {
        log.debug("Request to save ServiceRelationship : {}", serviceRelationshipDTO);
        ServiceRelationship serviceRelationship = serviceRelationshipMapper.toEntity(serviceRelationshipDTO);
        serviceRelationship = serviceRelationshipRepository.save(serviceRelationship);
        return serviceRelationshipMapper.toDto(serviceRelationship);
    }

    /**
     * Get all the serviceRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRelationships");
        return serviceRelationshipRepository.findAll(pageable)
            .map(serviceRelationshipMapper::toDto);
    }


    /**
     * Get one serviceRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceRelationshipDTO> findOne(Long id) {
        log.debug("Request to get ServiceRelationship : {}", id);
        return serviceRelationshipRepository.findById(id)
            .map(serviceRelationshipMapper::toDto);
    }

    /**
     * Delete the serviceRelationship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceRelationship : {}", id);
        serviceRelationshipRepository.deleteById(id);
    }
}
