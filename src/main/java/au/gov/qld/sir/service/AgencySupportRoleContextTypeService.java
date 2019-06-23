package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.AgencySupportRoleContextType;
import au.gov.qld.sir.repository.AgencySupportRoleContextTypeRepository;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleContextTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AgencySupportRoleContextType}.
 */
@Service
@Transactional
public class AgencySupportRoleContextTypeService {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleContextTypeService.class);

    private final AgencySupportRoleContextTypeRepository agencySupportRoleContextTypeRepository;

    private final AgencySupportRoleContextTypeMapper agencySupportRoleContextTypeMapper;

    public AgencySupportRoleContextTypeService(AgencySupportRoleContextTypeRepository agencySupportRoleContextTypeRepository, AgencySupportRoleContextTypeMapper agencySupportRoleContextTypeMapper) {
        this.agencySupportRoleContextTypeRepository = agencySupportRoleContextTypeRepository;
        this.agencySupportRoleContextTypeMapper = agencySupportRoleContextTypeMapper;
    }

    /**
     * Save a agencySupportRoleContextType.
     *
     * @param agencySupportRoleContextTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public AgencySupportRoleContextTypeDTO save(AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO) {
        log.debug("Request to save AgencySupportRoleContextType : {}", agencySupportRoleContextTypeDTO);
        AgencySupportRoleContextType agencySupportRoleContextType = agencySupportRoleContextTypeMapper.toEntity(agencySupportRoleContextTypeDTO);
        agencySupportRoleContextType = agencySupportRoleContextTypeRepository.save(agencySupportRoleContextType);
        return agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextType);
    }

    /**
     * Get all the agencySupportRoleContextTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencySupportRoleContextTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgencySupportRoleContextTypes");
        return agencySupportRoleContextTypeRepository.findAll(pageable)
            .map(agencySupportRoleContextTypeMapper::toDto);
    }


    /**
     * Get one agencySupportRoleContextType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgencySupportRoleContextTypeDTO> findOne(Long id) {
        log.debug("Request to get AgencySupportRoleContextType : {}", id);
        return agencySupportRoleContextTypeRepository.findById(id)
            .map(agencySupportRoleContextTypeMapper::toDto);
    }

    /**
     * Delete the agencySupportRoleContextType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgencySupportRoleContextType : {}", id);
        agencySupportRoleContextTypeRepository.deleteById(id);
    }
}
