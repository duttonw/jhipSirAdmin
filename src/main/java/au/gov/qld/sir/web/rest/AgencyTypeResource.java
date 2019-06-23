package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.AgencyTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.AgencyTypeDTO;
import au.gov.qld.sir.service.dto.AgencyTypeCriteria;
import au.gov.qld.sir.service.AgencyTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.AgencyType}.
 */
@RestController
@RequestMapping("/api")
public class AgencyTypeResource {

    private final Logger log = LoggerFactory.getLogger(AgencyTypeResource.class);

    private static final String ENTITY_NAME = "agencyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgencyTypeService agencyTypeService;

    private final AgencyTypeQueryService agencyTypeQueryService;

    public AgencyTypeResource(AgencyTypeService agencyTypeService, AgencyTypeQueryService agencyTypeQueryService) {
        this.agencyTypeService = agencyTypeService;
        this.agencyTypeQueryService = agencyTypeQueryService;
    }

    /**
     * {@code POST  /agency-types} : Create a new agencyType.
     *
     * @param agencyTypeDTO the agencyTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agencyTypeDTO, or with status {@code 400 (Bad Request)} if the agencyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agency-types")
    public ResponseEntity<AgencyTypeDTO> createAgencyType(@Valid @RequestBody AgencyTypeDTO agencyTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AgencyType : {}", agencyTypeDTO);
        if (agencyTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new agencyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgencyTypeDTO result = agencyTypeService.save(agencyTypeDTO);
        return ResponseEntity.created(new URI("/api/agency-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agency-types} : Updates an existing agencyType.
     *
     * @param agencyTypeDTO the agencyTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agencyTypeDTO,
     * or with status {@code 400 (Bad Request)} if the agencyTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agencyTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agency-types")
    public ResponseEntity<AgencyTypeDTO> updateAgencyType(@Valid @RequestBody AgencyTypeDTO agencyTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AgencyType : {}", agencyTypeDTO);
        if (agencyTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgencyTypeDTO result = agencyTypeService.save(agencyTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agencyTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agency-types} : get all the agencyTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agencyTypes in body.
     */
    @GetMapping("/agency-types")
    public ResponseEntity<List<AgencyTypeDTO>> getAllAgencyTypes(AgencyTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AgencyTypes by criteria: {}", criteria);
        Page<AgencyTypeDTO> page = agencyTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /agency-types/count} : count all the agencyTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/agency-types/count")
    public ResponseEntity<Long> countAgencyTypes(AgencyTypeCriteria criteria) {
        log.debug("REST request to count AgencyTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(agencyTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agency-types/:id} : get the "id" agencyType.
     *
     * @param id the id of the agencyTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agencyTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agency-types/{id}")
    public ResponseEntity<AgencyTypeDTO> getAgencyType(@PathVariable Long id) {
        log.debug("REST request to get AgencyType : {}", id);
        Optional<AgencyTypeDTO> agencyTypeDTO = agencyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agencyTypeDTO);
    }

    /**
     * {@code DELETE  /agency-types/:id} : delete the "id" agencyType.
     *
     * @param id the id of the agencyTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agency-types/{id}")
    public ResponseEntity<Void> deleteAgencyType(@PathVariable Long id) {
        log.debug("REST request to delete AgencyType : {}", id);
        agencyTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
