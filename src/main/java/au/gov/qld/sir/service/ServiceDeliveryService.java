package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceDelivery;
import au.gov.qld.sir.repository.ServiceDeliveryRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceDelivery}.
 */
@Service
@Transactional
public class ServiceDeliveryService {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryService.class);

    private final ServiceDeliveryRepository serviceDeliveryRepository;

    private final ServiceDeliveryMapper serviceDeliveryMapper;

    public ServiceDeliveryService(ServiceDeliveryRepository serviceDeliveryRepository, ServiceDeliveryMapper serviceDeliveryMapper) {
        this.serviceDeliveryRepository = serviceDeliveryRepository;
        this.serviceDeliveryMapper = serviceDeliveryMapper;
    }

    /**
     * Save a serviceDelivery.
     *
     * @param serviceDeliveryDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDeliveryDTO save(ServiceDeliveryDTO serviceDeliveryDTO) {
        log.debug("Request to save ServiceDelivery : {}", serviceDeliveryDTO);
        ServiceDelivery serviceDelivery = serviceDeliveryMapper.toEntity(serviceDeliveryDTO);
        serviceDelivery = serviceDeliveryRepository.save(serviceDelivery);
        return serviceDeliveryMapper.toDto(serviceDelivery);
    }

    /**
     * Get all the serviceDeliveries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceDeliveries");
        return serviceDeliveryRepository.findAll(pageable)
            .map(serviceDeliveryMapper::toDto);
    }


    /**
     * Get one serviceDelivery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDeliveryDTO> findOne(Long id) {
        log.debug("Request to get ServiceDelivery : {}", id);
        return serviceDeliveryRepository.findById(id)
            .map(serviceDeliveryMapper::toDto);
    }

    /**
     * Delete the serviceDelivery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceDelivery : {}", id);
        serviceDeliveryRepository.deleteById(id);
    }
}
