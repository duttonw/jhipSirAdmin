package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.repository.ServiceGroupRepository;
import au.gov.qld.sir.service.dto.ServiceGroupDTO;
import au.gov.qld.sir.service.mapper.ServiceGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceGroup}.
 */
@Service
@Transactional
public class ServiceGroupService {

    private final Logger log = LoggerFactory.getLogger(ServiceGroupService.class);

    private final ServiceGroupRepository serviceGroupRepository;

    private final ServiceGroupMapper serviceGroupMapper;

    public ServiceGroupService(ServiceGroupRepository serviceGroupRepository, ServiceGroupMapper serviceGroupMapper) {
        this.serviceGroupRepository = serviceGroupRepository;
        this.serviceGroupMapper = serviceGroupMapper;
    }

    /**
     * Save a serviceGroup.
     *
     * @param serviceGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceGroupDTO save(ServiceGroupDTO serviceGroupDTO) {
        log.debug("Request to save ServiceGroup : {}", serviceGroupDTO);
        ServiceGroup serviceGroup = serviceGroupMapper.toEntity(serviceGroupDTO);
        serviceGroup = serviceGroupRepository.save(serviceGroup);
        return serviceGroupMapper.toDto(serviceGroup);
    }

    /**
     * Get all the serviceGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceGroups");
        return serviceGroupRepository.findAll(pageable)
            .map(serviceGroupMapper::toDto);
    }


    /**
     * Get one serviceGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceGroupDTO> findOne(Long id) {
        log.debug("Request to get ServiceGroup : {}", id);
        return serviceGroupRepository.findById(id)
            .map(serviceGroupMapper::toDto);
    }

    /**
     * Delete the serviceGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceGroup : {}", id);
        serviceGroupRepository.deleteById(id);
    }
}
