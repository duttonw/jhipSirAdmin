package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceEventTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceEventTypeDTO;
import au.gov.qld.sir.service.dto.ServiceEventTypeCriteria;
import au.gov.qld.sir.service.ServiceEventTypeQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceEventType}.
 */
@RestController
@RequestMapping("/api")
public class ServiceEventTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceEventTypeResource.class);

    private static final String ENTITY_NAME = "serviceEventType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceEventTypeService serviceEventTypeService;

    private final ServiceEventTypeQueryService serviceEventTypeQueryService;

    public ServiceEventTypeResource(ServiceEventTypeService serviceEventTypeService, ServiceEventTypeQueryService serviceEventTypeQueryService) {
        this.serviceEventTypeService = serviceEventTypeService;
        this.serviceEventTypeQueryService = serviceEventTypeQueryService;
    }

    /**
     * {@code POST  /service-event-types} : Create a new serviceEventType.
     *
     * @param serviceEventTypeDTO the serviceEventTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceEventTypeDTO, or with status {@code 400 (Bad Request)} if the serviceEventType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-event-types")
    public ResponseEntity<ServiceEventTypeDTO> createServiceEventType(@Valid @RequestBody ServiceEventTypeDTO serviceEventTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceEventType : {}", serviceEventTypeDTO);
        if (serviceEventTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceEventType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceEventTypeDTO result = serviceEventTypeService.save(serviceEventTypeDTO);
        return ResponseEntity.created(new URI("/api/service-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-event-types} : Updates an existing serviceEventType.
     *
     * @param serviceEventTypeDTO the serviceEventTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceEventTypeDTO,
     * or with status {@code 400 (Bad Request)} if the serviceEventTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceEventTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-event-types")
    public ResponseEntity<ServiceEventTypeDTO> updateServiceEventType(@Valid @RequestBody ServiceEventTypeDTO serviceEventTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceEventType : {}", serviceEventTypeDTO);
        if (serviceEventTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceEventTypeDTO result = serviceEventTypeService.save(serviceEventTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceEventTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-event-types} : get all the serviceEventTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceEventTypes in body.
     */
    @GetMapping("/service-event-types")
    public ResponseEntity<List<ServiceEventTypeDTO>> getAllServiceEventTypes(ServiceEventTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceEventTypes by criteria: {}", criteria);
        Page<ServiceEventTypeDTO> page = serviceEventTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-event-types/count} : count all the serviceEventTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-event-types/count")
    public ResponseEntity<Long> countServiceEventTypes(ServiceEventTypeCriteria criteria) {
        log.debug("REST request to count ServiceEventTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceEventTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-event-types/:id} : get the "id" serviceEventType.
     *
     * @param id the id of the serviceEventTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceEventTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-event-types/{id}")
    public ResponseEntity<ServiceEventTypeDTO> getServiceEventType(@PathVariable Long id) {
        log.debug("REST request to get ServiceEventType : {}", id);
        Optional<ServiceEventTypeDTO> serviceEventTypeDTO = serviceEventTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceEventTypeDTO);
    }

    /**
     * {@code DELETE  /service-event-types/:id} : delete the "id" serviceEventType.
     *
     * @param id the id of the serviceEventTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-event-types/{id}")
    public ResponseEntity<Void> deleteServiceEventType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceEventType : {}", id);
        serviceEventTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
