package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceEventType;
import au.gov.qld.sir.repository.ServiceEventTypeRepository;
import au.gov.qld.sir.service.dto.ServiceEventTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceEventTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceEventType}.
 */
@Service
@Transactional
public class ServiceEventTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceEventTypeService.class);

    private final ServiceEventTypeRepository serviceEventTypeRepository;

    private final ServiceEventTypeMapper serviceEventTypeMapper;

    public ServiceEventTypeService(ServiceEventTypeRepository serviceEventTypeRepository, ServiceEventTypeMapper serviceEventTypeMapper) {
        this.serviceEventTypeRepository = serviceEventTypeRepository;
        this.serviceEventTypeMapper = serviceEventTypeMapper;
    }

    /**
     * Save a serviceEventType.
     *
     * @param serviceEventTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceEventTypeDTO save(ServiceEventTypeDTO serviceEventTypeDTO) {
        log.debug("Request to save ServiceEventType : {}", serviceEventTypeDTO);
        ServiceEventType serviceEventType = serviceEventTypeMapper.toEntity(serviceEventTypeDTO);
        serviceEventType = serviceEventTypeRepository.save(serviceEventType);
        return serviceEventTypeMapper.toDto(serviceEventType);
    }

    /**
     * Get all the serviceEventTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEventTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceEventTypes");
        return serviceEventTypeRepository.findAll(pageable)
            .map(serviceEventTypeMapper::toDto);
    }


    /**
     * Get one serviceEventType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceEventTypeDTO> findOne(Long id) {
        log.debug("Request to get ServiceEventType : {}", id);
        return serviceEventTypeRepository.findById(id)
            .map(serviceEventTypeMapper::toDto);
    }

    /**
     * Delete the serviceEventType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceEventType : {}", id);
        serviceEventTypeRepository.deleteById(id);
    }
}
