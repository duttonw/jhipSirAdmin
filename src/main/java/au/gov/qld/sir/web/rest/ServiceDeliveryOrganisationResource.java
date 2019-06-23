package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceDeliveryOrganisationService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationDTO;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationCriteria;
import au.gov.qld.sir.service.ServiceDeliveryOrganisationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceDeliveryOrganisation}.
 */
@RestController
@RequestMapping("/api")
public class ServiceDeliveryOrganisationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryOrganisationResource.class);

    private static final String ENTITY_NAME = "serviceDeliveryOrganisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceDeliveryOrganisationService serviceDeliveryOrganisationService;

    private final ServiceDeliveryOrganisationQueryService serviceDeliveryOrganisationQueryService;

    public ServiceDeliveryOrganisationResource(ServiceDeliveryOrganisationService serviceDeliveryOrganisationService, ServiceDeliveryOrganisationQueryService serviceDeliveryOrganisationQueryService) {
        this.serviceDeliveryOrganisationService = serviceDeliveryOrganisationService;
        this.serviceDeliveryOrganisationQueryService = serviceDeliveryOrganisationQueryService;
    }

    /**
     * {@code POST  /service-delivery-organisations} : Create a new serviceDeliveryOrganisation.
     *
     * @param serviceDeliveryOrganisationDTO the serviceDeliveryOrganisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceDeliveryOrganisationDTO, or with status {@code 400 (Bad Request)} if the serviceDeliveryOrganisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-delivery-organisations")
    public ResponseEntity<ServiceDeliveryOrganisationDTO> createServiceDeliveryOrganisation(@Valid @RequestBody ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceDeliveryOrganisation : {}", serviceDeliveryOrganisationDTO);
        if (serviceDeliveryOrganisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceDeliveryOrganisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceDeliveryOrganisationDTO result = serviceDeliveryOrganisationService.save(serviceDeliveryOrganisationDTO);
        return ResponseEntity.created(new URI("/api/service-delivery-organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-delivery-organisations} : Updates an existing serviceDeliveryOrganisation.
     *
     * @param serviceDeliveryOrganisationDTO the serviceDeliveryOrganisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDeliveryOrganisationDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDeliveryOrganisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDeliveryOrganisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-delivery-organisations")
    public ResponseEntity<ServiceDeliveryOrganisationDTO> updateServiceDeliveryOrganisation(@Valid @RequestBody ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceDeliveryOrganisation : {}", serviceDeliveryOrganisationDTO);
        if (serviceDeliveryOrganisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceDeliveryOrganisationDTO result = serviceDeliveryOrganisationService.save(serviceDeliveryOrganisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDeliveryOrganisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-delivery-organisations} : get all the serviceDeliveryOrganisations.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceDeliveryOrganisations in body.
     */
    @GetMapping("/service-delivery-organisations")
    public ResponseEntity<List<ServiceDeliveryOrganisationDTO>> getAllServiceDeliveryOrganisations(ServiceDeliveryOrganisationCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceDeliveryOrganisations by criteria: {}", criteria);
        Page<ServiceDeliveryOrganisationDTO> page = serviceDeliveryOrganisationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-delivery-organisations/count} : count all the serviceDeliveryOrganisations.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-delivery-organisations/count")
    public ResponseEntity<Long> countServiceDeliveryOrganisations(ServiceDeliveryOrganisationCriteria criteria) {
        log.debug("REST request to count ServiceDeliveryOrganisations by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceDeliveryOrganisationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-delivery-organisations/:id} : get the "id" serviceDeliveryOrganisation.
     *
     * @param id the id of the serviceDeliveryOrganisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDeliveryOrganisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-delivery-organisations/{id}")
    public ResponseEntity<ServiceDeliveryOrganisationDTO> getServiceDeliveryOrganisation(@PathVariable Long id) {
        log.debug("REST request to get ServiceDeliveryOrganisation : {}", id);
        Optional<ServiceDeliveryOrganisationDTO> serviceDeliveryOrganisationDTO = serviceDeliveryOrganisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceDeliveryOrganisationDTO);
    }

    /**
     * {@code DELETE  /service-delivery-organisations/:id} : delete the "id" serviceDeliveryOrganisation.
     *
     * @param id the id of the serviceDeliveryOrganisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-delivery-organisations/{id}")
    public ResponseEntity<Void> deleteServiceDeliveryOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete ServiceDeliveryOrganisation : {}", id);
        serviceDeliveryOrganisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
