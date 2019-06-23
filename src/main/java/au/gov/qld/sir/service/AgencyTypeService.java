package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.AgencyType;
import au.gov.qld.sir.repository.AgencyTypeRepository;
import au.gov.qld.sir.service.dto.AgencyTypeDTO;
import au.gov.qld.sir.service.mapper.AgencyTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AgencyType}.
 */
@Service
@Transactional
public class AgencyTypeService {

    private final Logger log = LoggerFactory.getLogger(AgencyTypeService.class);

    private final AgencyTypeRepository agencyTypeRepository;

    private final AgencyTypeMapper agencyTypeMapper;

    public AgencyTypeService(AgencyTypeRepository agencyTypeRepository, AgencyTypeMapper agencyTypeMapper) {
        this.agencyTypeRepository = agencyTypeRepository;
        this.agencyTypeMapper = agencyTypeMapper;
    }

    /**
     * Save a agencyType.
     *
     * @param agencyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public AgencyTypeDTO save(AgencyTypeDTO agencyTypeDTO) {
        log.debug("Request to save AgencyType : {}", agencyTypeDTO);
        AgencyType agencyType = agencyTypeMapper.toEntity(agencyTypeDTO);
        agencyType = agencyTypeRepository.save(agencyType);
        return agencyTypeMapper.toDto(agencyType);
    }

    /**
     * Get all the agencyTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgencyTypes");
        return agencyTypeRepository.findAll(pageable)
            .map(agencyTypeMapper::toDto);
    }


    /**
     * Get one agencyType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgencyTypeDTO> findOne(Long id) {
        log.debug("Request to get AgencyType : {}", id);
        return agencyTypeRepository.findById(id)
            .map(agencyTypeMapper::toDto);
    }

    /**
     * Delete the agencyType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgencyType : {}", id);
        agencyTypeRepository.deleteById(id);
    }
}
