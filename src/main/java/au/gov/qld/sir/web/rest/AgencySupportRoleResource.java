package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.AgencySupportRoleService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.AgencySupportRoleDTO;
import au.gov.qld.sir.service.dto.AgencySupportRoleCriteria;
import au.gov.qld.sir.service.AgencySupportRoleQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.AgencySupportRole}.
 */
@RestController
@RequestMapping("/api")
public class AgencySupportRoleResource {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleResource.class);

    private static final String ENTITY_NAME = "agencySupportRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgencySupportRoleService agencySupportRoleService;

    private final AgencySupportRoleQueryService agencySupportRoleQueryService;

    public AgencySupportRoleResource(AgencySupportRoleService agencySupportRoleService, AgencySupportRoleQueryService agencySupportRoleQueryService) {
        this.agencySupportRoleService = agencySupportRoleService;
        this.agencySupportRoleQueryService = agencySupportRoleQueryService;
    }

    /**
     * {@code POST  /agency-support-roles} : Create a new agencySupportRole.
     *
     * @param agencySupportRoleDTO the agencySupportRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agencySupportRoleDTO, or with status {@code 400 (Bad Request)} if the agencySupportRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agency-support-roles")
    public ResponseEntity<AgencySupportRoleDTO> createAgencySupportRole(@Valid @RequestBody AgencySupportRoleDTO agencySupportRoleDTO) throws URISyntaxException {
        log.debug("REST request to save AgencySupportRole : {}", agencySupportRoleDTO);
        if (agencySupportRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new agencySupportRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgencySupportRoleDTO result = agencySupportRoleService.save(agencySupportRoleDTO);
        return ResponseEntity.created(new URI("/api/agency-support-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agency-support-roles} : Updates an existing agencySupportRole.
     *
     * @param agencySupportRoleDTO the agencySupportRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agencySupportRoleDTO,
     * or with status {@code 400 (Bad Request)} if the agencySupportRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agencySupportRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agency-support-roles")
    public ResponseEntity<AgencySupportRoleDTO> updateAgencySupportRole(@Valid @RequestBody AgencySupportRoleDTO agencySupportRoleDTO) throws URISyntaxException {
        log.debug("REST request to update AgencySupportRole : {}", agencySupportRoleDTO);
        if (agencySupportRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgencySupportRoleDTO result = agencySupportRoleService.save(agencySupportRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agencySupportRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agency-support-roles} : get all the agencySupportRoles.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agencySupportRoles in body.
     */
    @GetMapping("/agency-support-roles")
    public ResponseEntity<List<AgencySupportRoleDTO>> getAllAgencySupportRoles(AgencySupportRoleCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AgencySupportRoles by criteria: {}", criteria);
        Page<AgencySupportRoleDTO> page = agencySupportRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /agency-support-roles/count} : count all the agencySupportRoles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/agency-support-roles/count")
    public ResponseEntity<Long> countAgencySupportRoles(AgencySupportRoleCriteria criteria) {
        log.debug("REST request to count AgencySupportRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(agencySupportRoleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agency-support-roles/:id} : get the "id" agencySupportRole.
     *
     * @param id the id of the agencySupportRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agencySupportRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agency-support-roles/{id}")
    public ResponseEntity<AgencySupportRoleDTO> getAgencySupportRole(@PathVariable Long id) {
        log.debug("REST request to get AgencySupportRole : {}", id);
        Optional<AgencySupportRoleDTO> agencySupportRoleDTO = agencySupportRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agencySupportRoleDTO);
    }

    /**
     * {@code DELETE  /agency-support-roles/:id} : delete the "id" agencySupportRole.
     *
     * @param id the id of the agencySupportRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agency-support-roles/{id}")
    public ResponseEntity<Void> deleteAgencySupportRole(@PathVariable Long id) {
        log.debug("REST request to delete AgencySupportRole : {}", id);
        agencySupportRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
