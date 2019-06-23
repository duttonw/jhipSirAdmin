package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.IntegrationMappingService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.IntegrationMappingDTO;
import au.gov.qld.sir.service.dto.IntegrationMappingCriteria;
import au.gov.qld.sir.service.IntegrationMappingQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.IntegrationMapping}.
 */
@RestController
@RequestMapping("/api")
public class IntegrationMappingResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationMappingResource.class);

    private static final String ENTITY_NAME = "integrationMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrationMappingService integrationMappingService;

    private final IntegrationMappingQueryService integrationMappingQueryService;

    public IntegrationMappingResource(IntegrationMappingService integrationMappingService, IntegrationMappingQueryService integrationMappingQueryService) {
        this.integrationMappingService = integrationMappingService;
        this.integrationMappingQueryService = integrationMappingQueryService;
    }

    /**
     * {@code POST  /integration-mappings} : Create a new integrationMapping.
     *
     * @param integrationMappingDTO the integrationMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrationMappingDTO, or with status {@code 400 (Bad Request)} if the integrationMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integration-mappings")
    public ResponseEntity<IntegrationMappingDTO> createIntegrationMapping(@Valid @RequestBody IntegrationMappingDTO integrationMappingDTO) throws URISyntaxException {
        log.debug("REST request to save IntegrationMapping : {}", integrationMappingDTO);
        if (integrationMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new integrationMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntegrationMappingDTO result = integrationMappingService.save(integrationMappingDTO);
        return ResponseEntity.created(new URI("/api/integration-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integration-mappings} : Updates an existing integrationMapping.
     *
     * @param integrationMappingDTO the integrationMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationMappingDTO,
     * or with status {@code 400 (Bad Request)} if the integrationMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrationMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integration-mappings")
    public ResponseEntity<IntegrationMappingDTO> updateIntegrationMapping(@Valid @RequestBody IntegrationMappingDTO integrationMappingDTO) throws URISyntaxException {
        log.debug("REST request to update IntegrationMapping : {}", integrationMappingDTO);
        if (integrationMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntegrationMappingDTO result = integrationMappingService.save(integrationMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrationMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /integration-mappings} : get all the integrationMappings.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrationMappings in body.
     */
    @GetMapping("/integration-mappings")
    public ResponseEntity<List<IntegrationMappingDTO>> getAllIntegrationMappings(IntegrationMappingCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get IntegrationMappings by criteria: {}", criteria);
        Page<IntegrationMappingDTO> page = integrationMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /integration-mappings/count} : count all the integrationMappings.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/integration-mappings/count")
    public ResponseEntity<Long> countIntegrationMappings(IntegrationMappingCriteria criteria) {
        log.debug("REST request to count IntegrationMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(integrationMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /integration-mappings/:id} : get the "id" integrationMapping.
     *
     * @param id the id of the integrationMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrationMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integration-mappings/{id}")
    public ResponseEntity<IntegrationMappingDTO> getIntegrationMapping(@PathVariable Long id) {
        log.debug("REST request to get IntegrationMapping : {}", id);
        Optional<IntegrationMappingDTO> integrationMappingDTO = integrationMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrationMappingDTO);
    }

    /**
     * {@code DELETE  /integration-mappings/:id} : delete the "id" integrationMapping.
     *
     * @param id the id of the integrationMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integration-mappings/{id}")
    public ResponseEntity<Void> deleteIntegrationMapping(@PathVariable Long id) {
        log.debug("REST request to delete IntegrationMapping : {}", id);
        integrationMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
