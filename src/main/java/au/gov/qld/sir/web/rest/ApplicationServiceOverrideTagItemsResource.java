package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ApplicationServiceOverrideTagItemsService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsDTO;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagItemsQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationServiceOverrideTagItemsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagItemsResource.class);

    private static final String ENTITY_NAME = "applicationServiceOverrideTagItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationServiceOverrideTagItemsService applicationServiceOverrideTagItemsService;

    private final ApplicationServiceOverrideTagItemsQueryService applicationServiceOverrideTagItemsQueryService;

    public ApplicationServiceOverrideTagItemsResource(ApplicationServiceOverrideTagItemsService applicationServiceOverrideTagItemsService, ApplicationServiceOverrideTagItemsQueryService applicationServiceOverrideTagItemsQueryService) {
        this.applicationServiceOverrideTagItemsService = applicationServiceOverrideTagItemsService;
        this.applicationServiceOverrideTagItemsQueryService = applicationServiceOverrideTagItemsQueryService;
    }

    /**
     * {@code POST  /application-service-override-tag-items} : Create a new applicationServiceOverrideTagItems.
     *
     * @param applicationServiceOverrideTagItemsDTO the applicationServiceOverrideTagItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationServiceOverrideTagItemsDTO, or with status {@code 400 (Bad Request)} if the applicationServiceOverrideTagItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-service-override-tag-items")
    public ResponseEntity<ApplicationServiceOverrideTagItemsDTO> createApplicationServiceOverrideTagItems(@Valid @RequestBody ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationServiceOverrideTagItems : {}", applicationServiceOverrideTagItemsDTO);
        if (applicationServiceOverrideTagItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationServiceOverrideTagItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationServiceOverrideTagItemsDTO result = applicationServiceOverrideTagItemsService.save(applicationServiceOverrideTagItemsDTO);
        return ResponseEntity.created(new URI("/api/application-service-override-tag-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-service-override-tag-items} : Updates an existing applicationServiceOverrideTagItems.
     *
     * @param applicationServiceOverrideTagItemsDTO the applicationServiceOverrideTagItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServiceOverrideTagItemsDTO,
     * or with status {@code 400 (Bad Request)} if the applicationServiceOverrideTagItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationServiceOverrideTagItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-service-override-tag-items")
    public ResponseEntity<ApplicationServiceOverrideTagItemsDTO> updateApplicationServiceOverrideTagItems(@Valid @RequestBody ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationServiceOverrideTagItems : {}", applicationServiceOverrideTagItemsDTO);
        if (applicationServiceOverrideTagItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationServiceOverrideTagItemsDTO result = applicationServiceOverrideTagItemsService.save(applicationServiceOverrideTagItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServiceOverrideTagItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-service-override-tag-items} : get all the applicationServiceOverrideTagItems.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationServiceOverrideTagItems in body.
     */
    @GetMapping("/application-service-override-tag-items")
    public ResponseEntity<List<ApplicationServiceOverrideTagItemsDTO>> getAllApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItemsCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ApplicationServiceOverrideTagItems by criteria: {}", criteria);
        Page<ApplicationServiceOverrideTagItemsDTO> page = applicationServiceOverrideTagItemsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /application-service-override-tag-items/count} : count all the applicationServiceOverrideTagItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/application-service-override-tag-items/count")
    public ResponseEntity<Long> countApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItemsCriteria criteria) {
        log.debug("REST request to count ApplicationServiceOverrideTagItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationServiceOverrideTagItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-service-override-tag-items/:id} : get the "id" applicationServiceOverrideTagItems.
     *
     * @param id the id of the applicationServiceOverrideTagItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationServiceOverrideTagItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-service-override-tag-items/{id}")
    public ResponseEntity<ApplicationServiceOverrideTagItemsDTO> getApplicationServiceOverrideTagItems(@PathVariable Long id) {
        log.debug("REST request to get ApplicationServiceOverrideTagItems : {}", id);
        Optional<ApplicationServiceOverrideTagItemsDTO> applicationServiceOverrideTagItemsDTO = applicationServiceOverrideTagItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationServiceOverrideTagItemsDTO);
    }

    /**
     * {@code DELETE  /application-service-override-tag-items/:id} : delete the "id" applicationServiceOverrideTagItems.
     *
     * @param id the id of the applicationServiceOverrideTagItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-service-override-tag-items/{id}")
    public ResponseEntity<Void> deleteApplicationServiceOverrideTagItems(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationServiceOverrideTagItems : {}", id);
        applicationServiceOverrideTagItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
