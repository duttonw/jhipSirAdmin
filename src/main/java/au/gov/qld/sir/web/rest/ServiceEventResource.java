package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceEventService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceEventDTO;
import au.gov.qld.sir.service.dto.ServiceEventCriteria;
import au.gov.qld.sir.service.ServiceEventQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceEvent}.
 */
@RestController
@RequestMapping("/api")
public class ServiceEventResource {

    private final Logger log = LoggerFactory.getLogger(ServiceEventResource.class);

    private static final String ENTITY_NAME = "serviceEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceEventService serviceEventService;

    private final ServiceEventQueryService serviceEventQueryService;

    public ServiceEventResource(ServiceEventService serviceEventService, ServiceEventQueryService serviceEventQueryService) {
        this.serviceEventService = serviceEventService;
        this.serviceEventQueryService = serviceEventQueryService;
    }

    /**
     * {@code POST  /service-events} : Create a new serviceEvent.
     *
     * @param serviceEventDTO the serviceEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceEventDTO, or with status {@code 400 (Bad Request)} if the serviceEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-events")
    public ResponseEntity<ServiceEventDTO> createServiceEvent(@Valid @RequestBody ServiceEventDTO serviceEventDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceEvent : {}", serviceEventDTO);
        if (serviceEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceEventDTO result = serviceEventService.save(serviceEventDTO);
        return ResponseEntity.created(new URI("/api/service-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-events} : Updates an existing serviceEvent.
     *
     * @param serviceEventDTO the serviceEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceEventDTO,
     * or with status {@code 400 (Bad Request)} if the serviceEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-events")
    public ResponseEntity<ServiceEventDTO> updateServiceEvent(@Valid @RequestBody ServiceEventDTO serviceEventDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceEvent : {}", serviceEventDTO);
        if (serviceEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceEventDTO result = serviceEventService.save(serviceEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-events} : get all the serviceEvents.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceEvents in body.
     */
    @GetMapping("/service-events")
    public ResponseEntity<List<ServiceEventDTO>> getAllServiceEvents(ServiceEventCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceEvents by criteria: {}", criteria);
        Page<ServiceEventDTO> page = serviceEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-events/count} : count all the serviceEvents.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-events/count")
    public ResponseEntity<Long> countServiceEvents(ServiceEventCriteria criteria) {
        log.debug("REST request to count ServiceEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-events/:id} : get the "id" serviceEvent.
     *
     * @param id the id of the serviceEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-events/{id}")
    public ResponseEntity<ServiceEventDTO> getServiceEvent(@PathVariable Long id) {
        log.debug("REST request to get ServiceEvent : {}", id);
        Optional<ServiceEventDTO> serviceEventDTO = serviceEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceEventDTO);
    }

    /**
     * {@code DELETE  /service-events/:id} : delete the "id" serviceEvent.
     *
     * @param id the id of the serviceEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-events/{id}")
    public ResponseEntity<Void> deleteServiceEvent(@PathVariable Long id) {
        log.debug("REST request to delete ServiceEvent : {}", id);
        serviceEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
