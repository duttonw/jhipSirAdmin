package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.repository.AgencySupportRoleRepository;
import au.gov.qld.sir.service.dto.AgencySupportRoleDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AgencySupportRole}.
 */
@Service
@Transactional
public class AgencySupportRoleService {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleService.class);

    private final AgencySupportRoleRepository agencySupportRoleRepository;

    private final AgencySupportRoleMapper agencySupportRoleMapper;

    public AgencySupportRoleService(AgencySupportRoleRepository agencySupportRoleRepository, AgencySupportRoleMapper agencySupportRoleMapper) {
        this.agencySupportRoleRepository = agencySupportRoleRepository;
        this.agencySupportRoleMapper = agencySupportRoleMapper;
    }

    /**
     * Save a agencySupportRole.
     *
     * @param agencySupportRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public AgencySupportRoleDTO save(AgencySupportRoleDTO agencySupportRoleDTO) {
        log.debug("Request to save AgencySupportRole : {}", agencySupportRoleDTO);
        AgencySupportRole agencySupportRole = agencySupportRoleMapper.toEntity(agencySupportRoleDTO);
        agencySupportRole = agencySupportRoleRepository.save(agencySupportRole);
        return agencySupportRoleMapper.toDto(agencySupportRole);
    }

    /**
     * Get all the agencySupportRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencySupportRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgencySupportRoles");
        return agencySupportRoleRepository.findAll(pageable)
            .map(agencySupportRoleMapper::toDto);
    }


    /**
     * Get one agencySupportRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgencySupportRoleDTO> findOne(Long id) {
        log.debug("Request to get AgencySupportRole : {}", id);
        return agencySupportRoleRepository.findById(id)
            .map(agencySupportRoleMapper::toDto);
    }

    /**
     * Delete the agencySupportRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgencySupportRole : {}", id);
        agencySupportRoleRepository.deleteById(id);
    }
}
