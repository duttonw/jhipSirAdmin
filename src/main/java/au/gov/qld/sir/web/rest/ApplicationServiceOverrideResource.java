package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ApplicationServiceOverrideService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideDTO;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ApplicationServiceOverride}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationServiceOverrideResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideResource.class);

    private static final String ENTITY_NAME = "applicationServiceOverride";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationServiceOverrideService applicationServiceOverrideService;

    private final ApplicationServiceOverrideQueryService applicationServiceOverrideQueryService;

    public ApplicationServiceOverrideResource(ApplicationServiceOverrideService applicationServiceOverrideService, ApplicationServiceOverrideQueryService applicationServiceOverrideQueryService) {
        this.applicationServiceOverrideService = applicationServiceOverrideService;
        this.applicationServiceOverrideQueryService = applicationServiceOverrideQueryService;
    }

    /**
     * {@code POST  /application-service-overrides} : Create a new applicationServiceOverride.
     *
     * @param applicationServiceOverrideDTO the applicationServiceOverrideDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationServiceOverrideDTO, or with status {@code 400 (Bad Request)} if the applicationServiceOverride has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-service-overrides")
    public ResponseEntity<ApplicationServiceOverrideDTO> createApplicationServiceOverride(@Valid @RequestBody ApplicationServiceOverrideDTO applicationServiceOverrideDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationServiceOverride : {}", applicationServiceOverrideDTO);
        if (applicationServiceOverrideDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationServiceOverride cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationServiceOverrideDTO result = applicationServiceOverrideService.save(applicationServiceOverrideDTO);
        return ResponseEntity.created(new URI("/api/application-service-overrides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-service-overrides} : Updates an existing applicationServiceOverride.
     *
     * @param applicationServiceOverrideDTO the applicationServiceOverrideDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServiceOverrideDTO,
     * or with status {@code 400 (Bad Request)} if the applicationServiceOverrideDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationServiceOverrideDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-service-overrides")
    public ResponseEntity<ApplicationServiceOverrideDTO> updateApplicationServiceOverride(@Valid @RequestBody ApplicationServiceOverrideDTO applicationServiceOverrideDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationServiceOverride : {}", applicationServiceOverrideDTO);
        if (applicationServiceOverrideDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationServiceOverrideDTO result = applicationServiceOverrideService.save(applicationServiceOverrideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServiceOverrideDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-service-overrides} : get all the applicationServiceOverrides.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationServiceOverrides in body.
     */
    @GetMapping("/application-service-overrides")
    public ResponseEntity<List<ApplicationServiceOverrideDTO>> getAllApplicationServiceOverrides(ApplicationServiceOverrideCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ApplicationServiceOverrides by criteria: {}", criteria);
        Page<ApplicationServiceOverrideDTO> page = applicationServiceOverrideQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /application-service-overrides/count} : count all the applicationServiceOverrides.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/application-service-overrides/count")
    public ResponseEntity<Long> countApplicationServiceOverrides(ApplicationServiceOverrideCriteria criteria) {
        log.debug("REST request to count ApplicationServiceOverrides by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationServiceOverrideQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-service-overrides/:id} : get the "id" applicationServiceOverride.
     *
     * @param id the id of the applicationServiceOverrideDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationServiceOverrideDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-service-overrides/{id}")
    public ResponseEntity<ApplicationServiceOverrideDTO> getApplicationServiceOverride(@PathVariable Long id) {
        log.debug("REST request to get ApplicationServiceOverride : {}", id);
        Optional<ApplicationServiceOverrideDTO> applicationServiceOverrideDTO = applicationServiceOverrideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationServiceOverrideDTO);
    }

    /**
     * {@code DELETE  /application-service-overrides/:id} : delete the "id" applicationServiceOverride.
     *
     * @param id the id of the applicationServiceOverrideDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-service-overrides/{id}")
    public ResponseEntity<Void> deleteApplicationServiceOverride(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationServiceOverride : {}", id);
        applicationServiceOverrideService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
