package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.repository.AgencyRepository;
import au.gov.qld.sir.service.dto.AgencyDTO;
import au.gov.qld.sir.service.mapper.AgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Agency}.
 */
@Service
@Transactional
public class AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyService.class);

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    public AgencyService(AgencyRepository agencyRepository, AgencyMapper agencyMapper) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
    }

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save.
     * @return the persisted entity.
     */
    public AgencyDTO save(AgencyDTO agencyDTO) {
        log.debug("Request to save Agency : {}", agencyDTO);
        Agency agency = agencyMapper.toEntity(agencyDTO);
        agency = agencyRepository.save(agency);
        return agencyMapper.toDto(agency);
    }

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll(pageable)
            .map(agencyMapper::toDto);
    }


    /**
     * Get one agency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgencyDTO> findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        return agencyRepository.findById(id)
            .map(agencyMapper::toDto);
    }

    /**
     * Delete the agency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.deleteById(id);
    }
}
