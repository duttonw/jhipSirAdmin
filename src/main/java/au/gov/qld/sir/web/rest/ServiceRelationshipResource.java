package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceRelationshipService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceRelationshipDTO;
import au.gov.qld.sir.service.dto.ServiceRelationshipCriteria;
import au.gov.qld.sir.service.ServiceRelationshipQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceRelationship}.
 */
@RestController
@RequestMapping("/api")
public class ServiceRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRelationshipResource.class);

    private static final String ENTITY_NAME = "serviceRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRelationshipService serviceRelationshipService;

    private final ServiceRelationshipQueryService serviceRelationshipQueryService;

    public ServiceRelationshipResource(ServiceRelationshipService serviceRelationshipService, ServiceRelationshipQueryService serviceRelationshipQueryService) {
        this.serviceRelationshipService = serviceRelationshipService;
        this.serviceRelationshipQueryService = serviceRelationshipQueryService;
    }

    /**
     * {@code POST  /service-relationships} : Create a new serviceRelationship.
     *
     * @param serviceRelationshipDTO the serviceRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRelationshipDTO, or with status {@code 400 (Bad Request)} if the serviceRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-relationships")
    public ResponseEntity<ServiceRelationshipDTO> createServiceRelationship(@Valid @RequestBody ServiceRelationshipDTO serviceRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceRelationship : {}", serviceRelationshipDTO);
        if (serviceRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRelationshipDTO result = serviceRelationshipService.save(serviceRelationshipDTO);
        return ResponseEntity.created(new URI("/api/service-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-relationships} : Updates an existing serviceRelationship.
     *
     * @param serviceRelationshipDTO the serviceRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-relationships")
    public ResponseEntity<ServiceRelationshipDTO> updateServiceRelationship(@Valid @RequestBody ServiceRelationshipDTO serviceRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceRelationship : {}", serviceRelationshipDTO);
        if (serviceRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceRelationshipDTO result = serviceRelationshipService.save(serviceRelationshipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceRelationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-relationships} : get all the serviceRelationships.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRelationships in body.
     */
    @GetMapping("/service-relationships")
    public ResponseEntity<List<ServiceRelationshipDTO>> getAllServiceRelationships(ServiceRelationshipCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceRelationships by criteria: {}", criteria);
        Page<ServiceRelationshipDTO> page = serviceRelationshipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-relationships/count} : count all the serviceRelationships.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-relationships/count")
    public ResponseEntity<Long> countServiceRelationships(ServiceRelationshipCriteria criteria) {
        log.debug("REST request to count ServiceRelationships by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceRelationshipQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-relationships/:id} : get the "id" serviceRelationship.
     *
     * @param id the id of the serviceRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-relationships/{id}")
    public ResponseEntity<ServiceRelationshipDTO> getServiceRelationship(@PathVariable Long id) {
        log.debug("REST request to get ServiceRelationship : {}", id);
        Optional<ServiceRelationshipDTO> serviceRelationshipDTO = serviceRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRelationshipDTO);
    }

    /**
     * {@code DELETE  /service-relationships/:id} : delete the "id" serviceRelationship.
     *
     * @param id the id of the serviceRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-relationships/{id}")
    public ResponseEntity<Void> deleteServiceRelationship(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRelationship : {}", id);
        serviceRelationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
