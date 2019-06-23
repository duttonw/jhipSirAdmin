package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.LocationPhoneService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.LocationPhoneDTO;
import au.gov.qld.sir.service.dto.LocationPhoneCriteria;
import au.gov.qld.sir.service.LocationPhoneQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.LocationPhone}.
 */
@RestController
@RequestMapping("/api")
public class LocationPhoneResource {

    private final Logger log = LoggerFactory.getLogger(LocationPhoneResource.class);

    private static final String ENTITY_NAME = "locationPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationPhoneService locationPhoneService;

    private final LocationPhoneQueryService locationPhoneQueryService;

    public LocationPhoneResource(LocationPhoneService locationPhoneService, LocationPhoneQueryService locationPhoneQueryService) {
        this.locationPhoneService = locationPhoneService;
        this.locationPhoneQueryService = locationPhoneQueryService;
    }

    /**
     * {@code POST  /location-phones} : Create a new locationPhone.
     *
     * @param locationPhoneDTO the locationPhoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationPhoneDTO, or with status {@code 400 (Bad Request)} if the locationPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-phones")
    public ResponseEntity<LocationPhoneDTO> createLocationPhone(@Valid @RequestBody LocationPhoneDTO locationPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save LocationPhone : {}", locationPhoneDTO);
        if (locationPhoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationPhoneDTO result = locationPhoneService.save(locationPhoneDTO);
        return ResponseEntity.created(new URI("/api/location-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-phones} : Updates an existing locationPhone.
     *
     * @param locationPhoneDTO the locationPhoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationPhoneDTO,
     * or with status {@code 400 (Bad Request)} if the locationPhoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationPhoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-phones")
    public ResponseEntity<LocationPhoneDTO> updateLocationPhone(@Valid @RequestBody LocationPhoneDTO locationPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update LocationPhone : {}", locationPhoneDTO);
        if (locationPhoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationPhoneDTO result = locationPhoneService.save(locationPhoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-phones} : get all the locationPhones.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationPhones in body.
     */
    @GetMapping("/location-phones")
    public ResponseEntity<List<LocationPhoneDTO>> getAllLocationPhones(LocationPhoneCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get LocationPhones by criteria: {}", criteria);
        Page<LocationPhoneDTO> page = locationPhoneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /location-phones/count} : count all the locationPhones.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/location-phones/count")
    public ResponseEntity<Long> countLocationPhones(LocationPhoneCriteria criteria) {
        log.debug("REST request to count LocationPhones by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationPhoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-phones/:id} : get the "id" locationPhone.
     *
     * @param id the id of the locationPhoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationPhoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-phones/{id}")
    public ResponseEntity<LocationPhoneDTO> getLocationPhone(@PathVariable Long id) {
        log.debug("REST request to get LocationPhone : {}", id);
        Optional<LocationPhoneDTO> locationPhoneDTO = locationPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationPhoneDTO);
    }

    /**
     * {@code DELETE  /location-phones/:id} : delete the "id" locationPhone.
     *
     * @param id the id of the locationPhoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-phones/{id}")
    public ResponseEntity<Void> deleteLocationPhone(@PathVariable Long id) {
        log.debug("REST request to delete LocationPhone : {}", id);
        locationPhoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
