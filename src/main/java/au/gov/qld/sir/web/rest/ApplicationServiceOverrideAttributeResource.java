package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ApplicationServiceOverrideAttributeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeDTO;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideAttributeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationServiceOverrideAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideAttributeResource.class);

    private static final String ENTITY_NAME = "applicationServiceOverrideAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationServiceOverrideAttributeService applicationServiceOverrideAttributeService;

    private final ApplicationServiceOverrideAttributeQueryService applicationServiceOverrideAttributeQueryService;

    public ApplicationServiceOverrideAttributeResource(ApplicationServiceOverrideAttributeService applicationServiceOverrideAttributeService, ApplicationServiceOverrideAttributeQueryService applicationServiceOverrideAttributeQueryService) {
        this.applicationServiceOverrideAttributeService = applicationServiceOverrideAttributeService;
        this.applicationServiceOverrideAttributeQueryService = applicationServiceOverrideAttributeQueryService;
    }

    /**
     * {@code POST  /application-service-override-attributes} : Create a new applicationServiceOverrideAttribute.
     *
     * @param applicationServiceOverrideAttributeDTO the applicationServiceOverrideAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationServiceOverrideAttributeDTO, or with status {@code 400 (Bad Request)} if the applicationServiceOverrideAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-service-override-attributes")
    public ResponseEntity<ApplicationServiceOverrideAttributeDTO> createApplicationServiceOverrideAttribute(@Valid @RequestBody ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationServiceOverrideAttribute : {}", applicationServiceOverrideAttributeDTO);
        if (applicationServiceOverrideAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationServiceOverrideAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationServiceOverrideAttributeDTO result = applicationServiceOverrideAttributeService.save(applicationServiceOverrideAttributeDTO);
        return ResponseEntity.created(new URI("/api/application-service-override-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-service-override-attributes} : Updates an existing applicationServiceOverrideAttribute.
     *
     * @param applicationServiceOverrideAttributeDTO the applicationServiceOverrideAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServiceOverrideAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the applicationServiceOverrideAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationServiceOverrideAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-service-override-attributes")
    public ResponseEntity<ApplicationServiceOverrideAttributeDTO> updateApplicationServiceOverrideAttribute(@Valid @RequestBody ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationServiceOverrideAttribute : {}", applicationServiceOverrideAttributeDTO);
        if (applicationServiceOverrideAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationServiceOverrideAttributeDTO result = applicationServiceOverrideAttributeService.save(applicationServiceOverrideAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServiceOverrideAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-service-override-attributes} : get all the applicationServiceOverrideAttributes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationServiceOverrideAttributes in body.
     */
    @GetMapping("/application-service-override-attributes")
    public ResponseEntity<List<ApplicationServiceOverrideAttributeDTO>> getAllApplicationServiceOverrideAttributes(ApplicationServiceOverrideAttributeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ApplicationServiceOverrideAttributes by criteria: {}", criteria);
        Page<ApplicationServiceOverrideAttributeDTO> page = applicationServiceOverrideAttributeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /application-service-override-attributes/count} : count all the applicationServiceOverrideAttributes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/application-service-override-attributes/count")
    public ResponseEntity<Long> countApplicationServiceOverrideAttributes(ApplicationServiceOverrideAttributeCriteria criteria) {
        log.debug("REST request to count ApplicationServiceOverrideAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationServiceOverrideAttributeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-service-override-attributes/:id} : get the "id" applicationServiceOverrideAttribute.
     *
     * @param id the id of the applicationServiceOverrideAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationServiceOverrideAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-service-override-attributes/{id}")
    public ResponseEntity<ApplicationServiceOverrideAttributeDTO> getApplicationServiceOverrideAttribute(@PathVariable Long id) {
        log.debug("REST request to get ApplicationServiceOverrideAttribute : {}", id);
        Optional<ApplicationServiceOverrideAttributeDTO> applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationServiceOverrideAttributeDTO);
    }

    /**
     * {@code DELETE  /application-service-override-attributes/:id} : delete the "id" applicationServiceOverrideAttribute.
     *
     * @param id the id of the applicationServiceOverrideAttributeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-service-override-attributes/{id}")
    public ResponseEntity<Void> deleteApplicationServiceOverrideAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationServiceOverrideAttribute : {}", id);
        applicationServiceOverrideAttributeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
