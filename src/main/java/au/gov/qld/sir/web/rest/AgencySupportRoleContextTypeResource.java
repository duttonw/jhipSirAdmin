package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.AgencySupportRoleContextTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeDTO;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.AgencySupportRoleContextTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.AgencySupportRoleContextType}.
 */
@RestController
@RequestMapping("/api")
public class AgencySupportRoleContextTypeResource {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleContextTypeResource.class);

    private static final String ENTITY_NAME = "agencySupportRoleContextType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgencySupportRoleContextTypeService agencySupportRoleContextTypeService;

    private final AgencySupportRoleContextTypeQueryService agencySupportRoleContextTypeQueryService;

    public AgencySupportRoleContextTypeResource(AgencySupportRoleContextTypeService agencySupportRoleContextTypeService, AgencySupportRoleContextTypeQueryService agencySupportRoleContextTypeQueryService) {
        this.agencySupportRoleContextTypeService = agencySupportRoleContextTypeService;
        this.agencySupportRoleContextTypeQueryService = agencySupportRoleContextTypeQueryService;
    }

    /**
     * {@code POST  /agency-support-role-context-types} : Create a new agencySupportRoleContextType.
     *
     * @param agencySupportRoleContextTypeDTO the agencySupportRoleContextTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agencySupportRoleContextTypeDTO, or with status {@code 400 (Bad Request)} if the agencySupportRoleContextType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agency-support-role-context-types")
    public ResponseEntity<AgencySupportRoleContextTypeDTO> createAgencySupportRoleContextType(@Valid @RequestBody AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AgencySupportRoleContextType : {}", agencySupportRoleContextTypeDTO);
        if (agencySupportRoleContextTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new agencySupportRoleContextType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgencySupportRoleContextTypeDTO result = agencySupportRoleContextTypeService.save(agencySupportRoleContextTypeDTO);
        return ResponseEntity.created(new URI("/api/agency-support-role-context-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agency-support-role-context-types} : Updates an existing agencySupportRoleContextType.
     *
     * @param agencySupportRoleContextTypeDTO the agencySupportRoleContextTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agencySupportRoleContextTypeDTO,
     * or with status {@code 400 (Bad Request)} if the agencySupportRoleContextTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agencySupportRoleContextTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agency-support-role-context-types")
    public ResponseEntity<AgencySupportRoleContextTypeDTO> updateAgencySupportRoleContextType(@Valid @RequestBody AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AgencySupportRoleContextType : {}", agencySupportRoleContextTypeDTO);
        if (agencySupportRoleContextTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgencySupportRoleContextTypeDTO result = agencySupportRoleContextTypeService.save(agencySupportRoleContextTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agencySupportRoleContextTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agency-support-role-context-types} : get all the agencySupportRoleContextTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agencySupportRoleContextTypes in body.
     */
    @GetMapping("/agency-support-role-context-types")
    public ResponseEntity<List<AgencySupportRoleContextTypeDTO>> getAllAgencySupportRoleContextTypes(AgencySupportRoleContextTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AgencySupportRoleContextTypes by criteria: {}", criteria);
        Page<AgencySupportRoleContextTypeDTO> page = agencySupportRoleContextTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /agency-support-role-context-types/count} : count all the agencySupportRoleContextTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/agency-support-role-context-types/count")
    public ResponseEntity<Long> countAgencySupportRoleContextTypes(AgencySupportRoleContextTypeCriteria criteria) {
        log.debug("REST request to count AgencySupportRoleContextTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(agencySupportRoleContextTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agency-support-role-context-types/:id} : get the "id" agencySupportRoleContextType.
     *
     * @param id the id of the agencySupportRoleContextTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agencySupportRoleContextTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agency-support-role-context-types/{id}")
    public ResponseEntity<AgencySupportRoleContextTypeDTO> getAgencySupportRoleContextType(@PathVariable Long id) {
        log.debug("REST request to get AgencySupportRoleContextType : {}", id);
        Optional<AgencySupportRoleContextTypeDTO> agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agencySupportRoleContextTypeDTO);
    }

    /**
     * {@code DELETE  /agency-support-role-context-types/:id} : delete the "id" agencySupportRoleContextType.
     *
     * @param id the id of the agencySupportRoleContextTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agency-support-role-context-types/{id}")
    public ResponseEntity<Void> deleteAgencySupportRoleContextType(@PathVariable Long id) {
        log.debug("REST request to delete AgencySupportRoleContextType : {}", id);
        agencySupportRoleContextTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
