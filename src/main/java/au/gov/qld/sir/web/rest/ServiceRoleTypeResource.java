package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceRoleTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceRoleTypeDTO;
import au.gov.qld.sir.service.dto.ServiceRoleTypeCriteria;
import au.gov.qld.sir.service.ServiceRoleTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceRoleType}.
 */
@RestController
@RequestMapping("/api")
public class ServiceRoleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRoleTypeResource.class);

    private static final String ENTITY_NAME = "serviceRoleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRoleTypeService serviceRoleTypeService;

    private final ServiceRoleTypeQueryService serviceRoleTypeQueryService;

    public ServiceRoleTypeResource(ServiceRoleTypeService serviceRoleTypeService, ServiceRoleTypeQueryService serviceRoleTypeQueryService) {
        this.serviceRoleTypeService = serviceRoleTypeService;
        this.serviceRoleTypeQueryService = serviceRoleTypeQueryService;
    }

    /**
     * {@code POST  /service-role-types} : Create a new serviceRoleType.
     *
     * @param serviceRoleTypeDTO the serviceRoleTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRoleTypeDTO, or with status {@code 400 (Bad Request)} if the serviceRoleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-role-types")
    public ResponseEntity<ServiceRoleTypeDTO> createServiceRoleType(@Valid @RequestBody ServiceRoleTypeDTO serviceRoleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceRoleType : {}", serviceRoleTypeDTO);
        if (serviceRoleTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRoleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRoleTypeDTO result = serviceRoleTypeService.save(serviceRoleTypeDTO);
        return ResponseEntity.created(new URI("/api/service-role-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-role-types} : Updates an existing serviceRoleType.
     *
     * @param serviceRoleTypeDTO the serviceRoleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRoleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRoleTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRoleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-role-types")
    public ResponseEntity<ServiceRoleTypeDTO> updateServiceRoleType(@Valid @RequestBody ServiceRoleTypeDTO serviceRoleTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceRoleType : {}", serviceRoleTypeDTO);
        if (serviceRoleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceRoleTypeDTO result = serviceRoleTypeService.save(serviceRoleTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceRoleTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-role-types} : get all the serviceRoleTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRoleTypes in body.
     */
    @GetMapping("/service-role-types")
    public ResponseEntity<List<ServiceRoleTypeDTO>> getAllServiceRoleTypes(ServiceRoleTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceRoleTypes by criteria: {}", criteria);
        Page<ServiceRoleTypeDTO> page = serviceRoleTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-role-types/count} : count all the serviceRoleTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-role-types/count")
    public ResponseEntity<Long> countServiceRoleTypes(ServiceRoleTypeCriteria criteria) {
        log.debug("REST request to count ServiceRoleTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceRoleTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-role-types/:id} : get the "id" serviceRoleType.
     *
     * @param id the id of the serviceRoleTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRoleTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-role-types/{id}")
    public ResponseEntity<ServiceRoleTypeDTO> getServiceRoleType(@PathVariable Long id) {
        log.debug("REST request to get ServiceRoleType : {}", id);
        Optional<ServiceRoleTypeDTO> serviceRoleTypeDTO = serviceRoleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRoleTypeDTO);
    }

    /**
     * {@code DELETE  /service-role-types/:id} : delete the "id" serviceRoleType.
     *
     * @param id the id of the serviceRoleTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-role-types/{id}")
    public ResponseEntity<Void> deleteServiceRoleType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRoleType : {}", id);
        serviceRoleTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
