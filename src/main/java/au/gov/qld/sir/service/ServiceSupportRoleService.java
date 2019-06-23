package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.repository.ServiceSupportRoleRepository;
import au.gov.qld.sir.service.dto.ServiceSupportRoleDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceSupportRole}.
 */
@Service
@Transactional
public class ServiceSupportRoleService {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleService.class);

    private final ServiceSupportRoleRepository serviceSupportRoleRepository;

    private final ServiceSupportRoleMapper serviceSupportRoleMapper;

    public ServiceSupportRoleService(ServiceSupportRoleRepository serviceSupportRoleRepository, ServiceSupportRoleMapper serviceSupportRoleMapper) {
        this.serviceSupportRoleRepository = serviceSupportRoleRepository;
        this.serviceSupportRoleMapper = serviceSupportRoleMapper;
    }

    /**
     * Save a serviceSupportRole.
     *
     * @param serviceSupportRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceSupportRoleDTO save(ServiceSupportRoleDTO serviceSupportRoleDTO) {
        log.debug("Request to save ServiceSupportRole : {}", serviceSupportRoleDTO);
        ServiceSupportRole serviceSupportRole = serviceSupportRoleMapper.toEntity(serviceSupportRoleDTO);
        serviceSupportRole = serviceSupportRoleRepository.save(serviceSupportRole);
        return serviceSupportRoleMapper.toDto(serviceSupportRole);
    }

    /**
     * Get all the serviceSupportRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceSupportRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceSupportRoles");
        return serviceSupportRoleRepository.findAll(pageable)
            .map(serviceSupportRoleMapper::toDto);
    }


    /**
     * Get one serviceSupportRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceSupportRoleDTO> findOne(Long id) {
        log.debug("Request to get ServiceSupportRole : {}", id);
        return serviceSupportRoleRepository.findById(id)
            .map(serviceSupportRoleMapper::toDto);
    }

    /**
     * Delete the serviceSupportRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceSupportRole : {}", id);
        serviceSupportRoleRepository.deleteById(id);
    }
}
