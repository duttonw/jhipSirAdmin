package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.LocationTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.LocationTypeDTO;
import au.gov.qld.sir.service.dto.LocationTypeCriteria;
import au.gov.qld.sir.service.LocationTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.LocationType}.
 */
@RestController
@RequestMapping("/api")
public class LocationTypeResource {

    private final Logger log = LoggerFactory.getLogger(LocationTypeResource.class);

    private static final String ENTITY_NAME = "locationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationTypeService locationTypeService;

    private final LocationTypeQueryService locationTypeQueryService;

    public LocationTypeResource(LocationTypeService locationTypeService, LocationTypeQueryService locationTypeQueryService) {
        this.locationTypeService = locationTypeService;
        this.locationTypeQueryService = locationTypeQueryService;
    }

    /**
     * {@code POST  /location-types} : Create a new locationType.
     *
     * @param locationTypeDTO the locationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationTypeDTO, or with status {@code 400 (Bad Request)} if the locationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-types")
    public ResponseEntity<LocationTypeDTO> createLocationType(@Valid @RequestBody LocationTypeDTO locationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LocationType : {}", locationTypeDTO);
        if (locationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationTypeDTO result = locationTypeService.save(locationTypeDTO);
        return ResponseEntity.created(new URI("/api/location-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-types} : Updates an existing locationType.
     *
     * @param locationTypeDTO the locationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the locationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-types")
    public ResponseEntity<LocationTypeDTO> updateLocationType(@Valid @RequestBody LocationTypeDTO locationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LocationType : {}", locationTypeDTO);
        if (locationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationTypeDTO result = locationTypeService.save(locationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-types} : get all the locationTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationTypes in body.
     */
    @GetMapping("/location-types")
    public ResponseEntity<List<LocationTypeDTO>> getAllLocationTypes(LocationTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get LocationTypes by criteria: {}", criteria);
        Page<LocationTypeDTO> page = locationTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /location-types/count} : count all the locationTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/location-types/count")
    public ResponseEntity<Long> countLocationTypes(LocationTypeCriteria criteria) {
        log.debug("REST request to count LocationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-types/:id} : get the "id" locationType.
     *
     * @param id the id of the locationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-types/{id}")
    public ResponseEntity<LocationTypeDTO> getLocationType(@PathVariable Long id) {
        log.debug("REST request to get LocationType : {}", id);
        Optional<LocationTypeDTO> locationTypeDTO = locationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationTypeDTO);
    }

    /**
     * {@code DELETE  /location-types/:id} : delete the "id" locationType.
     *
     * @param id the id of the locationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-types/{id}")
    public ResponseEntity<Void> deleteLocationType(@PathVariable Long id) {
        log.debug("REST request to delete LocationType : {}", id);
        locationTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
