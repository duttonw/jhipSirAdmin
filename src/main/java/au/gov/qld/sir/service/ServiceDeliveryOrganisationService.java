package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import au.gov.qld.sir.repository.ServiceDeliveryOrganisationRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryOrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceDeliveryOrganisation}.
 */
@Service
@Transactional
public class ServiceDeliveryOrganisationService {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryOrganisationService.class);

    private final ServiceDeliveryOrganisationRepository serviceDeliveryOrganisationRepository;

    private final ServiceDeliveryOrganisationMapper serviceDeliveryOrganisationMapper;

    public ServiceDeliveryOrganisationService(ServiceDeliveryOrganisationRepository serviceDeliveryOrganisationRepository, ServiceDeliveryOrganisationMapper serviceDeliveryOrganisationMapper) {
        this.serviceDeliveryOrganisationRepository = serviceDeliveryOrganisationRepository;
        this.serviceDeliveryOrganisationMapper = serviceDeliveryOrganisationMapper;
    }

    /**
     * Save a serviceDeliveryOrganisation.
     *
     * @param serviceDeliveryOrganisationDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDeliveryOrganisationDTO save(ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO) {
        log.debug("Request to save ServiceDeliveryOrganisation : {}", serviceDeliveryOrganisationDTO);
        ServiceDeliveryOrganisation serviceDeliveryOrganisation = serviceDeliveryOrganisationMapper.toEntity(serviceDeliveryOrganisationDTO);
        serviceDeliveryOrganisation = serviceDeliveryOrganisationRepository.save(serviceDeliveryOrganisation);
        return serviceDeliveryOrganisationMapper.toDto(serviceDeliveryOrganisation);
    }

    /**
     * Get all the serviceDeliveryOrganisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryOrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceDeliveryOrganisations");
        return serviceDeliveryOrganisationRepository.findAll(pageable)
            .map(serviceDeliveryOrganisationMapper::toDto);
    }


    /**
     * Get one serviceDeliveryOrganisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDeliveryOrganisationDTO> findOne(Long id) {
        log.debug("Request to get ServiceDeliveryOrganisation : {}", id);
        return serviceDeliveryOrganisationRepository.findById(id)
            .map(serviceDeliveryOrganisationMapper::toDto);
    }

    /**
     * Delete the serviceDeliveryOrganisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceDeliveryOrganisation : {}", id);
        serviceDeliveryOrganisationRepository.deleteById(id);
    }
}
