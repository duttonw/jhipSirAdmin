package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.LocationAddressService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.LocationAddressDTO;
import au.gov.qld.sir.service.dto.LocationAddressCriteria;
import au.gov.qld.sir.service.LocationAddressQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.LocationAddress}.
 */
@RestController
@RequestMapping("/api")
public class LocationAddressResource {

    private final Logger log = LoggerFactory.getLogger(LocationAddressResource.class);

    private static final String ENTITY_NAME = "locationAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationAddressService locationAddressService;

    private final LocationAddressQueryService locationAddressQueryService;

    public LocationAddressResource(LocationAddressService locationAddressService, LocationAddressQueryService locationAddressQueryService) {
        this.locationAddressService = locationAddressService;
        this.locationAddressQueryService = locationAddressQueryService;
    }

    /**
     * {@code POST  /location-addresses} : Create a new locationAddress.
     *
     * @param locationAddressDTO the locationAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationAddressDTO, or with status {@code 400 (Bad Request)} if the locationAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-addresses")
    public ResponseEntity<LocationAddressDTO> createLocationAddress(@Valid @RequestBody LocationAddressDTO locationAddressDTO) throws URISyntaxException {
        log.debug("REST request to save LocationAddress : {}", locationAddressDTO);
        if (locationAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationAddressDTO result = locationAddressService.save(locationAddressDTO);
        return ResponseEntity.created(new URI("/api/location-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-addresses} : Updates an existing locationAddress.
     *
     * @param locationAddressDTO the locationAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationAddressDTO,
     * or with status {@code 400 (Bad Request)} if the locationAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-addresses")
    public ResponseEntity<LocationAddressDTO> updateLocationAddress(@Valid @RequestBody LocationAddressDTO locationAddressDTO) throws URISyntaxException {
        log.debug("REST request to update LocationAddress : {}", locationAddressDTO);
        if (locationAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationAddressDTO result = locationAddressService.save(locationAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-addresses} : get all the locationAddresses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationAddresses in body.
     */
    @GetMapping("/location-addresses")
    public ResponseEntity<List<LocationAddressDTO>> getAllLocationAddresses(LocationAddressCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get LocationAddresses by criteria: {}", criteria);
        Page<LocationAddressDTO> page = locationAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /location-addresses/count} : count all the locationAddresses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/location-addresses/count")
    public ResponseEntity<Long> countLocationAddresses(LocationAddressCriteria criteria) {
        log.debug("REST request to count LocationAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-addresses/:id} : get the "id" locationAddress.
     *
     * @param id the id of the locationAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-addresses/{id}")
    public ResponseEntity<LocationAddressDTO> getLocationAddress(@PathVariable Long id) {
        log.debug("REST request to get LocationAddress : {}", id);
        Optional<LocationAddressDTO> locationAddressDTO = locationAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationAddressDTO);
    }

    /**
     * {@code DELETE  /location-addresses/:id} : delete the "id" locationAddress.
     *
     * @param id the id of the locationAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-addresses/{id}")
    public ResponseEntity<Void> deleteLocationAddress(@PathVariable Long id) {
        log.debug("REST request to delete LocationAddress : {}", id);
        locationAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
