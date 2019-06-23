package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.OpeningHoursSpecification;
import au.gov.qld.sir.repository.OpeningHoursSpecificationRepository;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationDTO;
import au.gov.qld.sir.service.mapper.OpeningHoursSpecificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OpeningHoursSpecification}.
 */
@Service
@Transactional
public class OpeningHoursSpecificationService {

    private final Logger log = LoggerFactory.getLogger(OpeningHoursSpecificationService.class);

    private final OpeningHoursSpecificationRepository openingHoursSpecificationRepository;

    private final OpeningHoursSpecificationMapper openingHoursSpecificationMapper;

    public OpeningHoursSpecificationService(OpeningHoursSpecificationRepository openingHoursSpecificationRepository, OpeningHoursSpecificationMapper openingHoursSpecificationMapper) {
        this.openingHoursSpecificationRepository = openingHoursSpecificationRepository;
        this.openingHoursSpecificationMapper = openingHoursSpecificationMapper;
    }

    /**
     * Save a openingHoursSpecification.
     *
     * @param openingHoursSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    public OpeningHoursSpecificationDTO save(OpeningHoursSpecificationDTO openingHoursSpecificationDTO) {
        log.debug("Request to save OpeningHoursSpecification : {}", openingHoursSpecificationDTO);
        OpeningHoursSpecification openingHoursSpecification = openingHoursSpecificationMapper.toEntity(openingHoursSpecificationDTO);
        openingHoursSpecification = openingHoursSpecificationRepository.save(openingHoursSpecification);
        return openingHoursSpecificationMapper.toDto(openingHoursSpecification);
    }

    /**
     * Get all the openingHoursSpecifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OpeningHoursSpecificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OpeningHoursSpecifications");
        return openingHoursSpecificationRepository.findAll(pageable)
            .map(openingHoursSpecificationMapper::toDto);
    }


    /**
     * Get one openingHoursSpecification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpeningHoursSpecificationDTO> findOne(Long id) {
        log.debug("Request to get OpeningHoursSpecification : {}", id);
        return openingHoursSpecificationRepository.findById(id)
            .map(openingHoursSpecificationMapper::toDto);
    }

    /**
     * Delete the openingHoursSpecification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpeningHoursSpecification : {}", id);
        openingHoursSpecificationRepository.deleteById(id);
    }
}
