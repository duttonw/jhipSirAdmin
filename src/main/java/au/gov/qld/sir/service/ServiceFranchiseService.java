package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceFranchise;
import au.gov.qld.sir.repository.ServiceFranchiseRepository;
import au.gov.qld.sir.service.dto.ServiceFranchiseDTO;
import au.gov.qld.sir.service.mapper.ServiceFranchiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceFranchise}.
 */
@Service
@Transactional
public class ServiceFranchiseService {

    private final Logger log = LoggerFactory.getLogger(ServiceFranchiseService.class);

    private final ServiceFranchiseRepository serviceFranchiseRepository;

    private final ServiceFranchiseMapper serviceFranchiseMapper;

    public ServiceFranchiseService(ServiceFranchiseRepository serviceFranchiseRepository, ServiceFranchiseMapper serviceFranchiseMapper) {
        this.serviceFranchiseRepository = serviceFranchiseRepository;
        this.serviceFranchiseMapper = serviceFranchiseMapper;
    }

    /**
     * Save a serviceFranchise.
     *
     * @param serviceFranchiseDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceFranchiseDTO save(ServiceFranchiseDTO serviceFranchiseDTO) {
        log.debug("Request to save ServiceFranchise : {}", serviceFranchiseDTO);
        ServiceFranchise serviceFranchise = serviceFranchiseMapper.toEntity(serviceFranchiseDTO);
        serviceFranchise = serviceFranchiseRepository.save(serviceFranchise);
        return serviceFranchiseMapper.toDto(serviceFranchise);
    }

    /**
     * Get all the serviceFranchises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceFranchiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceFranchises");
        return serviceFranchiseRepository.findAll(pageable)
            .map(serviceFranchiseMapper::toDto);
    }


    /**
     * Get one serviceFranchise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceFranchiseDTO> findOne(Long id) {
        log.debug("Request to get ServiceFranchise : {}", id);
        return serviceFranchiseRepository.findById(id)
            .map(serviceFranchiseMapper::toDto);
    }

    /**
     * Delete the serviceFranchise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceFranchise : {}", id);
        serviceFranchiseRepository.deleteById(id);
    }
}
