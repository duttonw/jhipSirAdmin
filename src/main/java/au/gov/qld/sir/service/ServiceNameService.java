package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceName;
import au.gov.qld.sir.repository.ServiceNameRepository;
import au.gov.qld.sir.service.dto.ServiceNameDTO;
import au.gov.qld.sir.service.mapper.ServiceNameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceName}.
 */
@Service
@Transactional
public class ServiceNameService {

    private final Logger log = LoggerFactory.getLogger(ServiceNameService.class);

    private final ServiceNameRepository serviceNameRepository;

    private final ServiceNameMapper serviceNameMapper;

    public ServiceNameService(ServiceNameRepository serviceNameRepository, ServiceNameMapper serviceNameMapper) {
        this.serviceNameRepository = serviceNameRepository;
        this.serviceNameMapper = serviceNameMapper;
    }

    /**
     * Save a serviceName.
     *
     * @param serviceNameDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceNameDTO save(ServiceNameDTO serviceNameDTO) {
        log.debug("Request to save ServiceName : {}", serviceNameDTO);
        ServiceName serviceName = serviceNameMapper.toEntity(serviceNameDTO);
        serviceName = serviceNameRepository.save(serviceName);
        return serviceNameMapper.toDto(serviceName);
    }

    /**
     * Get all the serviceNames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceNameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceNames");
        return serviceNameRepository.findAll(pageable)
            .map(serviceNameMapper::toDto);
    }


    /**
     * Get one serviceName by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceNameDTO> findOne(Long id) {
        log.debug("Request to get ServiceName : {}", id);
        return serviceNameRepository.findById(id)
            .map(serviceNameMapper::toDto);
    }

    /**
     * Delete the serviceName by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceName : {}", id);
        serviceNameRepository.deleteById(id);
    }
}
