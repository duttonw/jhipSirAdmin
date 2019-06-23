package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceSupportRoleContextTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeDTO;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.ServiceSupportRoleContextTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceSupportRoleContextType}.
 */
@RestController
@RequestMapping("/api")
public class ServiceSupportRoleContextTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleContextTypeResource.class);

    private static final String ENTITY_NAME = "serviceSupportRoleContextType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceSupportRoleContextTypeService serviceSupportRoleContextTypeService;

    private final ServiceSupportRoleContextTypeQueryService serviceSupportRoleContextTypeQueryService;

    public ServiceSupportRoleContextTypeResource(ServiceSupportRoleContextTypeService serviceSupportRoleContextTypeService, ServiceSupportRoleContextTypeQueryService serviceSupportRoleContextTypeQueryService) {
        this.serviceSupportRoleContextTypeService = serviceSupportRoleContextTypeService;
        this.serviceSupportRoleContextTypeQueryService = serviceSupportRoleContextTypeQueryService;
    }

    /**
     * {@code POST  /service-support-role-context-types} : Create a new serviceSupportRoleContextType.
     *
     * @param serviceSupportRoleContextTypeDTO the serviceSupportRoleContextTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceSupportRoleContextTypeDTO, or with status {@code 400 (Bad Request)} if the serviceSupportRoleContextType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-support-role-context-types")
    public ResponseEntity<ServiceSupportRoleContextTypeDTO> createServiceSupportRoleContextType(@Valid @RequestBody ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceSupportRoleContextType : {}", serviceSupportRoleContextTypeDTO);
        if (serviceSupportRoleContextTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceSupportRoleContextType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSupportRoleContextTypeDTO result = serviceSupportRoleContextTypeService.save(serviceSupportRoleContextTypeDTO);
        return ResponseEntity.created(new URI("/api/service-support-role-context-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-support-role-context-types} : Updates an existing serviceSupportRoleContextType.
     *
     * @param serviceSupportRoleContextTypeDTO the serviceSupportRoleContextTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSupportRoleContextTypeDTO,
     * or with status {@code 400 (Bad Request)} if the serviceSupportRoleContextTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceSupportRoleContextTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-support-role-context-types")
    public ResponseEntity<ServiceSupportRoleContextTypeDTO> updateServiceSupportRoleContextType(@Valid @RequestBody ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceSupportRoleContextType : {}", serviceSupportRoleContextTypeDTO);
        if (serviceSupportRoleContextTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceSupportRoleContextTypeDTO result = serviceSupportRoleContextTypeService.save(serviceSupportRoleContextTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSupportRoleContextTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-support-role-context-types} : get all the serviceSupportRoleContextTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSupportRoleContextTypes in body.
     */
    @GetMapping("/service-support-role-context-types")
    public ResponseEntity<List<ServiceSupportRoleContextTypeDTO>> getAllServiceSupportRoleContextTypes(ServiceSupportRoleContextTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceSupportRoleContextTypes by criteria: {}", criteria);
        Page<ServiceSupportRoleContextTypeDTO> page = serviceSupportRoleContextTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-support-role-context-types/count} : count all the serviceSupportRoleContextTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-support-role-context-types/count")
    public ResponseEntity<Long> countServiceSupportRoleContextTypes(ServiceSupportRoleContextTypeCriteria criteria) {
        log.debug("REST request to count ServiceSupportRoleContextTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceSupportRoleContextTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-support-role-context-types/:id} : get the "id" serviceSupportRoleContextType.
     *
     * @param id the id of the serviceSupportRoleContextTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSupportRoleContextTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-support-role-context-types/{id}")
    public ResponseEntity<ServiceSupportRoleContextTypeDTO> getServiceSupportRoleContextType(@PathVariable Long id) {
        log.debug("REST request to get ServiceSupportRoleContextType : {}", id);
        Optional<ServiceSupportRoleContextTypeDTO> serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceSupportRoleContextTypeDTO);
    }

    /**
     * {@code DELETE  /service-support-role-context-types/:id} : delete the "id" serviceSupportRoleContextType.
     *
     * @param id the id of the serviceSupportRoleContextTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-support-role-context-types/{id}")
    public ResponseEntity<Void> deleteServiceSupportRoleContextType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceSupportRoleContextType : {}", id);
        serviceSupportRoleContextTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
