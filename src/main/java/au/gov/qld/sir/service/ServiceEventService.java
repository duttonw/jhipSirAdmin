package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceEvent;
import au.gov.qld.sir.repository.ServiceEventRepository;
import au.gov.qld.sir.service.dto.ServiceEventDTO;
import au.gov.qld.sir.service.mapper.ServiceEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceEvent}.
 */
@Service
@Transactional
public class ServiceEventService {

    private final Logger log = LoggerFactory.getLogger(ServiceEventService.class);

    private final ServiceEventRepository serviceEventRepository;

    private final ServiceEventMapper serviceEventMapper;

    public ServiceEventService(ServiceEventRepository serviceEventRepository, ServiceEventMapper serviceEventMapper) {
        this.serviceEventRepository = serviceEventRepository;
        this.serviceEventMapper = serviceEventMapper;
    }

    /**
     * Save a serviceEvent.
     *
     * @param serviceEventDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceEventDTO save(ServiceEventDTO serviceEventDTO) {
        log.debug("Request to save ServiceEvent : {}", serviceEventDTO);
        ServiceEvent serviceEvent = serviceEventMapper.toEntity(serviceEventDTO);
        serviceEvent = serviceEventRepository.save(serviceEvent);
        return serviceEventMapper.toDto(serviceEvent);
    }

    /**
     * Get all the serviceEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceEvents");
        return serviceEventRepository.findAll(pageable)
            .map(serviceEventMapper::toDto);
    }


    /**
     * Get one serviceEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceEventDTO> findOne(Long id) {
        log.debug("Request to get ServiceEvent : {}", id);
        return serviceEventRepository.findById(id)
            .map(serviceEventMapper::toDto);
    }

    /**
     * Delete the serviceEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceEvent : {}", id);
        serviceEventRepository.deleteById(id);
    }
}
