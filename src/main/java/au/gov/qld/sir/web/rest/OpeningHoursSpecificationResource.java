package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.OpeningHoursSpecificationService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationDTO;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationCriteria;
import au.gov.qld.sir.service.OpeningHoursSpecificationQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.OpeningHoursSpecification}.
 */
@RestController
@RequestMapping("/api")
public class OpeningHoursSpecificationResource {

    private final Logger log = LoggerFactory.getLogger(OpeningHoursSpecificationResource.class);

    private static final String ENTITY_NAME = "openingHoursSpecification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpeningHoursSpecificationService openingHoursSpecificationService;

    private final OpeningHoursSpecificationQueryService openingHoursSpecificationQueryService;

    public OpeningHoursSpecificationResource(OpeningHoursSpecificationService openingHoursSpecificationService, OpeningHoursSpecificationQueryService openingHoursSpecificationQueryService) {
        this.openingHoursSpecificationService = openingHoursSpecificationService;
        this.openingHoursSpecificationQueryService = openingHoursSpecificationQueryService;
    }

    /**
     * {@code POST  /opening-hours-specifications} : Create a new openingHoursSpecification.
     *
     * @param openingHoursSpecificationDTO the openingHoursSpecificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new openingHoursSpecificationDTO, or with status {@code 400 (Bad Request)} if the openingHoursSpecification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opening-hours-specifications")
    public ResponseEntity<OpeningHoursSpecificationDTO> createOpeningHoursSpecification(@Valid @RequestBody OpeningHoursSpecificationDTO openingHoursSpecificationDTO) throws URISyntaxException {
        log.debug("REST request to save OpeningHoursSpecification : {}", openingHoursSpecificationDTO);
        if (openingHoursSpecificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new openingHoursSpecification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpeningHoursSpecificationDTO result = openingHoursSpecificationService.save(openingHoursSpecificationDTO);
        return ResponseEntity.created(new URI("/api/opening-hours-specifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opening-hours-specifications} : Updates an existing openingHoursSpecification.
     *
     * @param openingHoursSpecificationDTO the openingHoursSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated openingHoursSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the openingHoursSpecificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the openingHoursSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opening-hours-specifications")
    public ResponseEntity<OpeningHoursSpecificationDTO> updateOpeningHoursSpecification(@Valid @RequestBody OpeningHoursSpecificationDTO openingHoursSpecificationDTO) throws URISyntaxException {
        log.debug("REST request to update OpeningHoursSpecification : {}", openingHoursSpecificationDTO);
        if (openingHoursSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpeningHoursSpecificationDTO result = openingHoursSpecificationService.save(openingHoursSpecificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, openingHoursSpecificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opening-hours-specifications} : get all the openingHoursSpecifications.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of openingHoursSpecifications in body.
     */
    @GetMapping("/opening-hours-specifications")
    public ResponseEntity<List<OpeningHoursSpecificationDTO>> getAllOpeningHoursSpecifications(OpeningHoursSpecificationCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get OpeningHoursSpecifications by criteria: {}", criteria);
        Page<OpeningHoursSpecificationDTO> page = openingHoursSpecificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /opening-hours-specifications/count} : count all the openingHoursSpecifications.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/opening-hours-specifications/count")
    public ResponseEntity<Long> countOpeningHoursSpecifications(OpeningHoursSpecificationCriteria criteria) {
        log.debug("REST request to count OpeningHoursSpecifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(openingHoursSpecificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opening-hours-specifications/:id} : get the "id" openingHoursSpecification.
     *
     * @param id the id of the openingHoursSpecificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the openingHoursSpecificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opening-hours-specifications/{id}")
    public ResponseEntity<OpeningHoursSpecificationDTO> getOpeningHoursSpecification(@PathVariable Long id) {
        log.debug("REST request to get OpeningHoursSpecification : {}", id);
        Optional<OpeningHoursSpecificationDTO> openingHoursSpecificationDTO = openingHoursSpecificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(openingHoursSpecificationDTO);
    }

    /**
     * {@code DELETE  /opening-hours-specifications/:id} : delete the "id" openingHoursSpecification.
     *
     * @param id the id of the openingHoursSpecificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opening-hours-specifications/{id}")
    public ResponseEntity<Void> deleteOpeningHoursSpecification(@PathVariable Long id) {
        log.debug("REST request to delete OpeningHoursSpecification : {}", id);
        openingHoursSpecificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
