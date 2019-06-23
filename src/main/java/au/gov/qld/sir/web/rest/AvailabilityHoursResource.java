package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.AvailabilityHoursService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.AvailabilityHoursDTO;
import au.gov.qld.sir.service.dto.AvailabilityHoursCriteria;
import au.gov.qld.sir.service.AvailabilityHoursQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.AvailabilityHours}.
 */
@RestController
@RequestMapping("/api")
public class AvailabilityHoursResource {

    private final Logger log = LoggerFactory.getLogger(AvailabilityHoursResource.class);

    private static final String ENTITY_NAME = "availabilityHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvailabilityHoursService availabilityHoursService;

    private final AvailabilityHoursQueryService availabilityHoursQueryService;

    public AvailabilityHoursResource(AvailabilityHoursService availabilityHoursService, AvailabilityHoursQueryService availabilityHoursQueryService) {
        this.availabilityHoursService = availabilityHoursService;
        this.availabilityHoursQueryService = availabilityHoursQueryService;
    }

    /**
     * {@code POST  /availability-hours} : Create a new availabilityHours.
     *
     * @param availabilityHoursDTO the availabilityHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new availabilityHoursDTO, or with status {@code 400 (Bad Request)} if the availabilityHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/availability-hours")
    public ResponseEntity<AvailabilityHoursDTO> createAvailabilityHours(@Valid @RequestBody AvailabilityHoursDTO availabilityHoursDTO) throws URISyntaxException {
        log.debug("REST request to save AvailabilityHours : {}", availabilityHoursDTO);
        if (availabilityHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new availabilityHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvailabilityHoursDTO result = availabilityHoursService.save(availabilityHoursDTO);
        return ResponseEntity.created(new URI("/api/availability-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /availability-hours} : Updates an existing availabilityHours.
     *
     * @param availabilityHoursDTO the availabilityHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated availabilityHoursDTO,
     * or with status {@code 400 (Bad Request)} if the availabilityHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the availabilityHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/availability-hours")
    public ResponseEntity<AvailabilityHoursDTO> updateAvailabilityHours(@Valid @RequestBody AvailabilityHoursDTO availabilityHoursDTO) throws URISyntaxException {
        log.debug("REST request to update AvailabilityHours : {}", availabilityHoursDTO);
        if (availabilityHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvailabilityHoursDTO result = availabilityHoursService.save(availabilityHoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, availabilityHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /availability-hours} : get all the availabilityHours.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of availabilityHours in body.
     */
    @GetMapping("/availability-hours")
    public ResponseEntity<List<AvailabilityHoursDTO>> getAllAvailabilityHours(AvailabilityHoursCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AvailabilityHours by criteria: {}", criteria);
        Page<AvailabilityHoursDTO> page = availabilityHoursQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /availability-hours/count} : count all the availabilityHours.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/availability-hours/count")
    public ResponseEntity<Long> countAvailabilityHours(AvailabilityHoursCriteria criteria) {
        log.debug("REST request to count AvailabilityHours by criteria: {}", criteria);
        return ResponseEntity.ok().body(availabilityHoursQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /availability-hours/:id} : get the "id" availabilityHours.
     *
     * @param id the id of the availabilityHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the availabilityHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/availability-hours/{id}")
    public ResponseEntity<AvailabilityHoursDTO> getAvailabilityHours(@PathVariable Long id) {
        log.debug("REST request to get AvailabilityHours : {}", id);
        Optional<AvailabilityHoursDTO> availabilityHoursDTO = availabilityHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(availabilityHoursDTO);
    }

    /**
     * {@code DELETE  /availability-hours/:id} : delete the "id" availabilityHours.
     *
     * @param id the id of the availabilityHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/availability-hours/{id}")
    public ResponseEntity<Void> deleteAvailabilityHours(@PathVariable Long id) {
        log.debug("REST request to delete AvailabilityHours : {}", id);
        availabilityHoursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
