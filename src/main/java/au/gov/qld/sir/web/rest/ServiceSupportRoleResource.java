package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceSupportRoleService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceSupportRoleDTO;
import au.gov.qld.sir.service.dto.ServiceSupportRoleCriteria;
import au.gov.qld.sir.service.ServiceSupportRoleQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceSupportRole}.
 */
@RestController
@RequestMapping("/api")
public class ServiceSupportRoleResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleResource.class);

    private static final String ENTITY_NAME = "serviceSupportRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceSupportRoleService serviceSupportRoleService;

    private final ServiceSupportRoleQueryService serviceSupportRoleQueryService;

    public ServiceSupportRoleResource(ServiceSupportRoleService serviceSupportRoleService, ServiceSupportRoleQueryService serviceSupportRoleQueryService) {
        this.serviceSupportRoleService = serviceSupportRoleService;
        this.serviceSupportRoleQueryService = serviceSupportRoleQueryService;
    }

    /**
     * {@code POST  /service-support-roles} : Create a new serviceSupportRole.
     *
     * @param serviceSupportRoleDTO the serviceSupportRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceSupportRoleDTO, or with status {@code 400 (Bad Request)} if the serviceSupportRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-support-roles")
    public ResponseEntity<ServiceSupportRoleDTO> createServiceSupportRole(@Valid @RequestBody ServiceSupportRoleDTO serviceSupportRoleDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceSupportRole : {}", serviceSupportRoleDTO);
        if (serviceSupportRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceSupportRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSupportRoleDTO result = serviceSupportRoleService.save(serviceSupportRoleDTO);
        return ResponseEntity.created(new URI("/api/service-support-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-support-roles} : Updates an existing serviceSupportRole.
     *
     * @param serviceSupportRoleDTO the serviceSupportRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSupportRoleDTO,
     * or with status {@code 400 (Bad Request)} if the serviceSupportRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceSupportRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-support-roles")
    public ResponseEntity<ServiceSupportRoleDTO> updateServiceSupportRole(@Valid @RequestBody ServiceSupportRoleDTO serviceSupportRoleDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceSupportRole : {}", serviceSupportRoleDTO);
        if (serviceSupportRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceSupportRoleDTO result = serviceSupportRoleService.save(serviceSupportRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSupportRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-support-roles} : get all the serviceSupportRoles.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSupportRoles in body.
     */
    @GetMapping("/service-support-roles")
    public ResponseEntity<List<ServiceSupportRoleDTO>> getAllServiceSupportRoles(ServiceSupportRoleCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceSupportRoles by criteria: {}", criteria);
        Page<ServiceSupportRoleDTO> page = serviceSupportRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-support-roles/count} : count all the serviceSupportRoles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-support-roles/count")
    public ResponseEntity<Long> countServiceSupportRoles(ServiceSupportRoleCriteria criteria) {
        log.debug("REST request to count ServiceSupportRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceSupportRoleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-support-roles/:id} : get the "id" serviceSupportRole.
     *
     * @param id the id of the serviceSupportRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSupportRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-support-roles/{id}")
    public ResponseEntity<ServiceSupportRoleDTO> getServiceSupportRole(@PathVariable Long id) {
        log.debug("REST request to get ServiceSupportRole : {}", id);
        Optional<ServiceSupportRoleDTO> serviceSupportRoleDTO = serviceSupportRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceSupportRoleDTO);
    }

    /**
     * {@code DELETE  /service-support-roles/:id} : delete the "id" serviceSupportRole.
     *
     * @param id the id of the serviceSupportRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-support-roles/{id}")
    public ResponseEntity<Void> deleteServiceSupportRole(@PathVariable Long id) {
        log.debug("REST request to delete ServiceSupportRole : {}", id);
        serviceSupportRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
