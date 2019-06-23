package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.LocationEmailService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.LocationEmailDTO;
import au.gov.qld.sir.service.dto.LocationEmailCriteria;
import au.gov.qld.sir.service.LocationEmailQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.LocationEmail}.
 */
@RestController
@RequestMapping("/api")
public class LocationEmailResource {

    private final Logger log = LoggerFactory.getLogger(LocationEmailResource.class);

    private static final String ENTITY_NAME = "locationEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationEmailService locationEmailService;

    private final LocationEmailQueryService locationEmailQueryService;

    public LocationEmailResource(LocationEmailService locationEmailService, LocationEmailQueryService locationEmailQueryService) {
        this.locationEmailService = locationEmailService;
        this.locationEmailQueryService = locationEmailQueryService;
    }

    /**
     * {@code POST  /location-emails} : Create a new locationEmail.
     *
     * @param locationEmailDTO the locationEmailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationEmailDTO, or with status {@code 400 (Bad Request)} if the locationEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-emails")
    public ResponseEntity<LocationEmailDTO> createLocationEmail(@Valid @RequestBody LocationEmailDTO locationEmailDTO) throws URISyntaxException {
        log.debug("REST request to save LocationEmail : {}", locationEmailDTO);
        if (locationEmailDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationEmailDTO result = locationEmailService.save(locationEmailDTO);
        return ResponseEntity.created(new URI("/api/location-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-emails} : Updates an existing locationEmail.
     *
     * @param locationEmailDTO the locationEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationEmailDTO,
     * or with status {@code 400 (Bad Request)} if the locationEmailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-emails")
    public ResponseEntity<LocationEmailDTO> updateLocationEmail(@Valid @RequestBody LocationEmailDTO locationEmailDTO) throws URISyntaxException {
        log.debug("REST request to update LocationEmail : {}", locationEmailDTO);
        if (locationEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationEmailDTO result = locationEmailService.save(locationEmailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationEmailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-emails} : get all the locationEmails.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationEmails in body.
     */
    @GetMapping("/location-emails")
    public ResponseEntity<List<LocationEmailDTO>> getAllLocationEmails(LocationEmailCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get LocationEmails by criteria: {}", criteria);
        Page<LocationEmailDTO> page = locationEmailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /location-emails/count} : count all the locationEmails.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/location-emails/count")
    public ResponseEntity<Long> countLocationEmails(LocationEmailCriteria criteria) {
        log.debug("REST request to count LocationEmails by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationEmailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-emails/:id} : get the "id" locationEmail.
     *
     * @param id the id of the locationEmailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationEmailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-emails/{id}")
    public ResponseEntity<LocationEmailDTO> getLocationEmail(@PathVariable Long id) {
        log.debug("REST request to get LocationEmail : {}", id);
        Optional<LocationEmailDTO> locationEmailDTO = locationEmailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationEmailDTO);
    }

    /**
     * {@code DELETE  /location-emails/:id} : delete the "id" locationEmail.
     *
     * @param id the id of the locationEmailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-emails/{id}")
    public ResponseEntity<Void> deleteLocationEmail(@PathVariable Long id) {
        log.debug("REST request to delete LocationEmail : {}", id);
        locationEmailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
