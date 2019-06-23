package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceTag;
import au.gov.qld.sir.repository.ServiceTagRepository;
import au.gov.qld.sir.service.dto.ServiceTagDTO;
import au.gov.qld.sir.service.mapper.ServiceTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceTag}.
 */
@Service
@Transactional
public class ServiceTagService {

    private final Logger log = LoggerFactory.getLogger(ServiceTagService.class);

    private final ServiceTagRepository serviceTagRepository;

    private final ServiceTagMapper serviceTagMapper;

    public ServiceTagService(ServiceTagRepository serviceTagRepository, ServiceTagMapper serviceTagMapper) {
        this.serviceTagRepository = serviceTagRepository;
        this.serviceTagMapper = serviceTagMapper;
    }

    /**
     * Save a serviceTag.
     *
     * @param serviceTagDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceTagDTO save(ServiceTagDTO serviceTagDTO) {
        log.debug("Request to save ServiceTag : {}", serviceTagDTO);
        ServiceTag serviceTag = serviceTagMapper.toEntity(serviceTagDTO);
        serviceTag = serviceTagRepository.save(serviceTag);
        return serviceTagMapper.toDto(serviceTag);
    }

    /**
     * Get all the serviceTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceTags");
        return serviceTagRepository.findAll(pageable)
            .map(serviceTagMapper::toDto);
    }


    /**
     * Get one serviceTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceTagDTO> findOne(Long id) {
        log.debug("Request to get ServiceTag : {}", id);
        return serviceTagRepository.findById(id)
            .map(serviceTagMapper::toDto);
    }

    /**
     * Delete the serviceTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceTag : {}", id);
        serviceTagRepository.deleteById(id);
    }
}
