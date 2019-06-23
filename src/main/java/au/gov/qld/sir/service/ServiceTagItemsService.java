package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceTagItems;
import au.gov.qld.sir.repository.ServiceTagItemsRepository;
import au.gov.qld.sir.service.dto.ServiceTagItemsDTO;
import au.gov.qld.sir.service.mapper.ServiceTagItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceTagItems}.
 */
@Service
@Transactional
public class ServiceTagItemsService {

    private final Logger log = LoggerFactory.getLogger(ServiceTagItemsService.class);

    private final ServiceTagItemsRepository serviceTagItemsRepository;

    private final ServiceTagItemsMapper serviceTagItemsMapper;

    public ServiceTagItemsService(ServiceTagItemsRepository serviceTagItemsRepository, ServiceTagItemsMapper serviceTagItemsMapper) {
        this.serviceTagItemsRepository = serviceTagItemsRepository;
        this.serviceTagItemsMapper = serviceTagItemsMapper;
    }

    /**
     * Save a serviceTagItems.
     *
     * @param serviceTagItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceTagItemsDTO save(ServiceTagItemsDTO serviceTagItemsDTO) {
        log.debug("Request to save ServiceTagItems : {}", serviceTagItemsDTO);
        ServiceTagItems serviceTagItems = serviceTagItemsMapper.toEntity(serviceTagItemsDTO);
        serviceTagItems = serviceTagItemsRepository.save(serviceTagItems);
        return serviceTagItemsMapper.toDto(serviceTagItems);
    }

    /**
     * Get all the serviceTagItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceTagItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceTagItems");
        return serviceTagItemsRepository.findAll(pageable)
            .map(serviceTagItemsMapper::toDto);
    }


    /**
     * Get one serviceTagItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceTagItemsDTO> findOne(Long id) {
        log.debug("Request to get ServiceTagItems : {}", id);
        return serviceTagItemsRepository.findById(id)
            .map(serviceTagItemsMapper::toDto);
    }

    /**
     * Delete the serviceTagItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceTagItems : {}", id);
        serviceTagItemsRepository.deleteById(id);
    }
}
