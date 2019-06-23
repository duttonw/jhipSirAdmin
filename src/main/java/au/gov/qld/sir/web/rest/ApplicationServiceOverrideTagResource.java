package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ApplicationServiceOverrideTagService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagDTO;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ApplicationServiceOverrideTag}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationServiceOverrideTagResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagResource.class);

    private static final String ENTITY_NAME = "applicationServiceOverrideTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationServiceOverrideTagService applicationServiceOverrideTagService;

    private final ApplicationServiceOverrideTagQueryService applicationServiceOverrideTagQueryService;

    public ApplicationServiceOverrideTagResource(ApplicationServiceOverrideTagService applicationServiceOverrideTagService, ApplicationServiceOverrideTagQueryService applicationServiceOverrideTagQueryService) {
        this.applicationServiceOverrideTagService = applicationServiceOverrideTagService;
        this.applicationServiceOverrideTagQueryService = applicationServiceOverrideTagQueryService;
    }

    /**
     * {@code POST  /application-service-override-tags} : Create a new applicationServiceOverrideTag.
     *
     * @param applicationServiceOverrideTagDTO the applicationServiceOverrideTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationServiceOverrideTagDTO, or with status {@code 400 (Bad Request)} if the applicationServiceOverrideTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-service-override-tags")
    public ResponseEntity<ApplicationServiceOverrideTagDTO> createApplicationServiceOverrideTag(@Valid @RequestBody ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationServiceOverrideTag : {}", applicationServiceOverrideTagDTO);
        if (applicationServiceOverrideTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationServiceOverrideTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationServiceOverrideTagDTO result = applicationServiceOverrideTagService.save(applicationServiceOverrideTagDTO);
        return ResponseEntity.created(new URI("/api/application-service-override-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-service-override-tags} : Updates an existing applicationServiceOverrideTag.
     *
     * @param applicationServiceOverrideTagDTO the applicationServiceOverrideTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServiceOverrideTagDTO,
     * or with status {@code 400 (Bad Request)} if the applicationServiceOverrideTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationServiceOverrideTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-service-override-tags")
    public ResponseEntity<ApplicationServiceOverrideTagDTO> updateApplicationServiceOverrideTag(@Valid @RequestBody ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationServiceOverrideTag : {}", applicationServiceOverrideTagDTO);
        if (applicationServiceOverrideTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationServiceOverrideTagDTO result = applicationServiceOverrideTagService.save(applicationServiceOverrideTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServiceOverrideTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-service-override-tags} : get all the applicationServiceOverrideTags.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationServiceOverrideTags in body.
     */
    @GetMapping("/application-service-override-tags")
    public ResponseEntity<List<ApplicationServiceOverrideTagDTO>> getAllApplicationServiceOverrideTags(ApplicationServiceOverrideTagCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ApplicationServiceOverrideTags by criteria: {}", criteria);
        Page<ApplicationServiceOverrideTagDTO> page = applicationServiceOverrideTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /application-service-override-tags/count} : count all the applicationServiceOverrideTags.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/application-service-override-tags/count")
    public ResponseEntity<Long> countApplicationServiceOverrideTags(ApplicationServiceOverrideTagCriteria criteria) {
        log.debug("REST request to count ApplicationServiceOverrideTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationServiceOverrideTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-service-override-tags/:id} : get the "id" applicationServiceOverrideTag.
     *
     * @param id the id of the applicationServiceOverrideTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationServiceOverrideTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-service-override-tags/{id}")
    public ResponseEntity<ApplicationServiceOverrideTagDTO> getApplicationServiceOverrideTag(@PathVariable Long id) {
        log.debug("REST request to get ApplicationServiceOverrideTag : {}", id);
        Optional<ApplicationServiceOverrideTagDTO> applicationServiceOverrideTagDTO = applicationServiceOverrideTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationServiceOverrideTagDTO);
    }

    /**
     * {@code DELETE  /application-service-override-tags/:id} : delete the "id" applicationServiceOverrideTag.
     *
     * @param id the id of the applicationServiceOverrideTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-service-override-tags/{id}")
    public ResponseEntity<Void> deleteApplicationServiceOverrideTag(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationServiceOverrideTag : {}", id);
        applicationServiceOverrideTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
