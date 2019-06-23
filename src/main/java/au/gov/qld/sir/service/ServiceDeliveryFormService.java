package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceDeliveryForm;
import au.gov.qld.sir.repository.ServiceDeliveryFormRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceDeliveryForm}.
 */
@Service
@Transactional
public class ServiceDeliveryFormService {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryFormService.class);

    private final ServiceDeliveryFormRepository serviceDeliveryFormRepository;

    private final ServiceDeliveryFormMapper serviceDeliveryFormMapper;

    public ServiceDeliveryFormService(ServiceDeliveryFormRepository serviceDeliveryFormRepository, ServiceDeliveryFormMapper serviceDeliveryFormMapper) {
        this.serviceDeliveryFormRepository = serviceDeliveryFormRepository;
        this.serviceDeliveryFormMapper = serviceDeliveryFormMapper;
    }

    /**
     * Save a serviceDeliveryForm.
     *
     * @param serviceDeliveryFormDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDeliveryFormDTO save(ServiceDeliveryFormDTO serviceDeliveryFormDTO) {
        log.debug("Request to save ServiceDeliveryForm : {}", serviceDeliveryFormDTO);
        ServiceDeliveryForm serviceDeliveryForm = serviceDeliveryFormMapper.toEntity(serviceDeliveryFormDTO);
        serviceDeliveryForm = serviceDeliveryFormRepository.save(serviceDeliveryForm);
        return serviceDeliveryFormMapper.toDto(serviceDeliveryForm);
    }

    /**
     * Get all the serviceDeliveryForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceDeliveryForms");
        return serviceDeliveryFormRepository.findAll(pageable)
            .map(serviceDeliveryFormMapper::toDto);
    }


    /**
     * Get one serviceDeliveryForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDeliveryFormDTO> findOne(Long id) {
        log.debug("Request to get ServiceDeliveryForm : {}", id);
        return serviceDeliveryFormRepository.findById(id)
            .map(serviceDeliveryFormMapper::toDto);
    }

    /**
     * Delete the serviceDeliveryForm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceDeliveryForm : {}", id);
        serviceDeliveryFormRepository.deleteById(id);
    }
}
