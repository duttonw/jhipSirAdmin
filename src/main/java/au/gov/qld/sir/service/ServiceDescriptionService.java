package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceDescription;
import au.gov.qld.sir.repository.ServiceDescriptionRepository;
import au.gov.qld.sir.service.dto.ServiceDescriptionDTO;
import au.gov.qld.sir.service.mapper.ServiceDescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceDescription}.
 */
@Service
@Transactional
public class ServiceDescriptionService {

    private final Logger log = LoggerFactory.getLogger(ServiceDescriptionService.class);

    private final ServiceDescriptionRepository serviceDescriptionRepository;

    private final ServiceDescriptionMapper serviceDescriptionMapper;

    public ServiceDescriptionService(ServiceDescriptionRepository serviceDescriptionRepository, ServiceDescriptionMapper serviceDescriptionMapper) {
        this.serviceDescriptionRepository = serviceDescriptionRepository;
        this.serviceDescriptionMapper = serviceDescriptionMapper;
    }

    /**
     * Save a serviceDescription.
     *
     * @param serviceDescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDescriptionDTO save(ServiceDescriptionDTO serviceDescriptionDTO) {
        log.debug("Request to save ServiceDescription : {}", serviceDescriptionDTO);
        ServiceDescription serviceDescription = serviceDescriptionMapper.toEntity(serviceDescriptionDTO);
        serviceDescription = serviceDescriptionRepository.save(serviceDescription);
        return serviceDescriptionMapper.toDto(serviceDescription);
    }

    /**
     * Get all the serviceDescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDescriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceDescriptions");
        return serviceDescriptionRepository.findAll(pageable)
            .map(serviceDescriptionMapper::toDto);
    }


    /**
     * Get one serviceDescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDescriptionDTO> findOne(Long id) {
        log.debug("Request to get ServiceDescription : {}", id);
        return serviceDescriptionRepository.findById(id)
            .map(serviceDescriptionMapper::toDto);
    }

    /**
     * Delete the serviceDescription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceDescription : {}", id);
        serviceDescriptionRepository.deleteById(id);
    }
}
