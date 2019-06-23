package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceEvidenceService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceEvidenceDTO;
import au.gov.qld.sir.service.dto.ServiceEvidenceCriteria;
import au.gov.qld.sir.service.ServiceEvidenceQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceEvidence}.
 */
@RestController
@RequestMapping("/api")
public class ServiceEvidenceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceEvidenceResource.class);

    private static final String ENTITY_NAME = "serviceEvidence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceEvidenceService serviceEvidenceService;

    private final ServiceEvidenceQueryService serviceEvidenceQueryService;

    public ServiceEvidenceResource(ServiceEvidenceService serviceEvidenceService, ServiceEvidenceQueryService serviceEvidenceQueryService) {
        this.serviceEvidenceService = serviceEvidenceService;
        this.serviceEvidenceQueryService = serviceEvidenceQueryService;
    }

    /**
     * {@code POST  /service-evidences} : Create a new serviceEvidence.
     *
     * @param serviceEvidenceDTO the serviceEvidenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceEvidenceDTO, or with status {@code 400 (Bad Request)} if the serviceEvidence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-evidences")
    public ResponseEntity<ServiceEvidenceDTO> createServiceEvidence(@Valid @RequestBody ServiceEvidenceDTO serviceEvidenceDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceEvidence : {}", serviceEvidenceDTO);
        if (serviceEvidenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceEvidence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceEvidenceDTO result = serviceEvidenceService.save(serviceEvidenceDTO);
        return ResponseEntity.created(new URI("/api/service-evidences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-evidences} : Updates an existing serviceEvidence.
     *
     * @param serviceEvidenceDTO the serviceEvidenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceEvidenceDTO,
     * or with status {@code 400 (Bad Request)} if the serviceEvidenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceEvidenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-evidences")
    public ResponseEntity<ServiceEvidenceDTO> updateServiceEvidence(@Valid @RequestBody ServiceEvidenceDTO serviceEvidenceDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceEvidence : {}", serviceEvidenceDTO);
        if (serviceEvidenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceEvidenceDTO result = serviceEvidenceService.save(serviceEvidenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceEvidenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-evidences} : get all the serviceEvidences.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceEvidences in body.
     */
    @GetMapping("/service-evidences")
    public ResponseEntity<List<ServiceEvidenceDTO>> getAllServiceEvidences(ServiceEvidenceCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceEvidences by criteria: {}", criteria);
        Page<ServiceEvidenceDTO> page = serviceEvidenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-evidences/count} : count all the serviceEvidences.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-evidences/count")
    public ResponseEntity<Long> countServiceEvidences(ServiceEvidenceCriteria criteria) {
        log.debug("REST request to count ServiceEvidences by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceEvidenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-evidences/:id} : get the "id" serviceEvidence.
     *
     * @param id the id of the serviceEvidenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceEvidenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-evidences/{id}")
    public ResponseEntity<ServiceEvidenceDTO> getServiceEvidence(@PathVariable Long id) {
        log.debug("REST request to get ServiceEvidence : {}", id);
        Optional<ServiceEvidenceDTO> serviceEvidenceDTO = serviceEvidenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceEvidenceDTO);
    }

    /**
     * {@code DELETE  /service-evidences/:id} : delete the "id" serviceEvidence.
     *
     * @param id the id of the serviceEvidenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-evidences/{id}")
    public ResponseEntity<Void> deleteServiceEvidence(@PathVariable Long id) {
        log.debug("REST request to delete ServiceEvidence : {}", id);
        serviceEvidenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
