package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceDescriptionService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceDescriptionDTO;
import au.gov.qld.sir.service.dto.ServiceDescriptionCriteria;
import au.gov.qld.sir.service.ServiceDescriptionQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceDescription}.
 */
@RestController
@RequestMapping("/api")
public class ServiceDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(ServiceDescriptionResource.class);

    private static final String ENTITY_NAME = "serviceDescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceDescriptionService serviceDescriptionService;

    private final ServiceDescriptionQueryService serviceDescriptionQueryService;

    public ServiceDescriptionResource(ServiceDescriptionService serviceDescriptionService, ServiceDescriptionQueryService serviceDescriptionQueryService) {
        this.serviceDescriptionService = serviceDescriptionService;
        this.serviceDescriptionQueryService = serviceDescriptionQueryService;
    }

    /**
     * {@code POST  /service-descriptions} : Create a new serviceDescription.
     *
     * @param serviceDescriptionDTO the serviceDescriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceDescriptionDTO, or with status {@code 400 (Bad Request)} if the serviceDescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-descriptions")
    public ResponseEntity<ServiceDescriptionDTO> createServiceDescription(@Valid @RequestBody ServiceDescriptionDTO serviceDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceDescription : {}", serviceDescriptionDTO);
        if (serviceDescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceDescriptionDTO result = serviceDescriptionService.save(serviceDescriptionDTO);
        return ResponseEntity.created(new URI("/api/service-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-descriptions} : Updates an existing serviceDescription.
     *
     * @param serviceDescriptionDTO the serviceDescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDescriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-descriptions")
    public ResponseEntity<ServiceDescriptionDTO> updateServiceDescription(@Valid @RequestBody ServiceDescriptionDTO serviceDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceDescription : {}", serviceDescriptionDTO);
        if (serviceDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceDescriptionDTO result = serviceDescriptionService.save(serviceDescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-descriptions} : get all the serviceDescriptions.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceDescriptions in body.
     */
    @GetMapping("/service-descriptions")
    public ResponseEntity<List<ServiceDescriptionDTO>> getAllServiceDescriptions(ServiceDescriptionCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceDescriptions by criteria: {}", criteria);
        Page<ServiceDescriptionDTO> page = serviceDescriptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-descriptions/count} : count all the serviceDescriptions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-descriptions/count")
    public ResponseEntity<Long> countServiceDescriptions(ServiceDescriptionCriteria criteria) {
        log.debug("REST request to count ServiceDescriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceDescriptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-descriptions/:id} : get the "id" serviceDescription.
     *
     * @param id the id of the serviceDescriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDescriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-descriptions/{id}")
    public ResponseEntity<ServiceDescriptionDTO> getServiceDescription(@PathVariable Long id) {
        log.debug("REST request to get ServiceDescription : {}", id);
        Optional<ServiceDescriptionDTO> serviceDescriptionDTO = serviceDescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceDescriptionDTO);
    }

    /**
     * {@code DELETE  /service-descriptions/:id} : delete the "id" serviceDescription.
     *
     * @param id the id of the serviceDescriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-descriptions/{id}")
    public ResponseEntity<Void> deleteServiceDescription(@PathVariable Long id) {
        log.debug("REST request to delete ServiceDescription : {}", id);
        serviceDescriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
