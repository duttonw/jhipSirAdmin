package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceRoleType;
import au.gov.qld.sir.repository.ServiceRoleTypeRepository;
import au.gov.qld.sir.service.dto.ServiceRoleTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceRoleTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceRoleType}.
 */
@Service
@Transactional
public class ServiceRoleTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceRoleTypeService.class);

    private final ServiceRoleTypeRepository serviceRoleTypeRepository;

    private final ServiceRoleTypeMapper serviceRoleTypeMapper;

    public ServiceRoleTypeService(ServiceRoleTypeRepository serviceRoleTypeRepository, ServiceRoleTypeMapper serviceRoleTypeMapper) {
        this.serviceRoleTypeRepository = serviceRoleTypeRepository;
        this.serviceRoleTypeMapper = serviceRoleTypeMapper;
    }

    /**
     * Save a serviceRoleType.
     *
     * @param serviceRoleTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRoleTypeDTO save(ServiceRoleTypeDTO serviceRoleTypeDTO) {
        log.debug("Request to save ServiceRoleType : {}", serviceRoleTypeDTO);
        ServiceRoleType serviceRoleType = serviceRoleTypeMapper.toEntity(serviceRoleTypeDTO);
        serviceRoleType = serviceRoleTypeRepository.save(serviceRoleType);
        return serviceRoleTypeMapper.toDto(serviceRoleType);
    }

    /**
     * Get all the serviceRoleTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRoleTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRoleTypes");
        return serviceRoleTypeRepository.findAll(pageable)
            .map(serviceRoleTypeMapper::toDto);
    }


    /**
     * Get one serviceRoleType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceRoleTypeDTO> findOne(Long id) {
        log.debug("Request to get ServiceRoleType : {}", id);
        return serviceRoleTypeRepository.findById(id)
            .map(serviceRoleTypeMapper::toDto);
    }

    /**
     * Delete the serviceRoleType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceRoleType : {}", id);
        serviceRoleTypeRepository.deleteById(id);
    }
}
