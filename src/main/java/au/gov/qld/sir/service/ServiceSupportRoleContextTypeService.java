package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceSupportRoleContextType;
import au.gov.qld.sir.repository.ServiceSupportRoleContextTypeRepository;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleContextTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceSupportRoleContextType}.
 */
@Service
@Transactional
public class ServiceSupportRoleContextTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleContextTypeService.class);

    private final ServiceSupportRoleContextTypeRepository serviceSupportRoleContextTypeRepository;

    private final ServiceSupportRoleContextTypeMapper serviceSupportRoleContextTypeMapper;

    public ServiceSupportRoleContextTypeService(ServiceSupportRoleContextTypeRepository serviceSupportRoleContextTypeRepository, ServiceSupportRoleContextTypeMapper serviceSupportRoleContextTypeMapper) {
        this.serviceSupportRoleContextTypeRepository = serviceSupportRoleContextTypeRepository;
        this.serviceSupportRoleContextTypeMapper = serviceSupportRoleContextTypeMapper;
    }

    /**
     * Save a serviceSupportRoleContextType.
     *
     * @param serviceSupportRoleContextTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceSupportRoleContextTypeDTO save(ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO) {
        log.debug("Request to save ServiceSupportRoleContextType : {}", serviceSupportRoleContextTypeDTO);
        ServiceSupportRoleContextType serviceSupportRoleContextType = serviceSupportRoleContextTypeMapper.toEntity(serviceSupportRoleContextTypeDTO);
        serviceSupportRoleContextType = serviceSupportRoleContextTypeRepository.save(serviceSupportRoleContextType);
        return serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextType);
    }

    /**
     * Get all the serviceSupportRoleContextTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceSupportRoleContextTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceSupportRoleContextTypes");
        return serviceSupportRoleContextTypeRepository.findAll(pageable)
            .map(serviceSupportRoleContextTypeMapper::toDto);
    }


    /**
     * Get one serviceSupportRoleContextType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceSupportRoleContextTypeDTO> findOne(Long id) {
        log.debug("Request to get ServiceSupportRoleContextType : {}", id);
        return serviceSupportRoleContextTypeRepository.findById(id)
            .map(serviceSupportRoleContextTypeMapper::toDto);
    }

    /**
     * Delete the serviceSupportRoleContextType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceSupportRoleContextType : {}", id);
        serviceSupportRoleContextTypeRepository.deleteById(id);
    }
}
