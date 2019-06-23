package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceDeliveryService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceDeliveryDTO;
import au.gov.qld.sir.service.dto.ServiceDeliveryCriteria;
import au.gov.qld.sir.service.ServiceDeliveryQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceDelivery}.
 */
@RestController
@RequestMapping("/api")
public class ServiceDeliveryResource {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryResource.class);

    private static final String ENTITY_NAME = "serviceDelivery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceDeliveryService serviceDeliveryService;

    private final ServiceDeliveryQueryService serviceDeliveryQueryService;

    public ServiceDeliveryResource(ServiceDeliveryService serviceDeliveryService, ServiceDeliveryQueryService serviceDeliveryQueryService) {
        this.serviceDeliveryService = serviceDeliveryService;
        this.serviceDeliveryQueryService = serviceDeliveryQueryService;
    }

    /**
     * {@code POST  /service-deliveries} : Create a new serviceDelivery.
     *
     * @param serviceDeliveryDTO the serviceDeliveryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceDeliveryDTO, or with status {@code 400 (Bad Request)} if the serviceDelivery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-deliveries")
    public ResponseEntity<ServiceDeliveryDTO> createServiceDelivery(@Valid @RequestBody ServiceDeliveryDTO serviceDeliveryDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceDelivery : {}", serviceDeliveryDTO);
        if (serviceDeliveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceDelivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceDeliveryDTO result = serviceDeliveryService.save(serviceDeliveryDTO);
        return ResponseEntity.created(new URI("/api/service-deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-deliveries} : Updates an existing serviceDelivery.
     *
     * @param serviceDeliveryDTO the serviceDeliveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDeliveryDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDeliveryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDeliveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-deliveries")
    public ResponseEntity<ServiceDeliveryDTO> updateServiceDelivery(@Valid @RequestBody ServiceDeliveryDTO serviceDeliveryDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceDelivery : {}", serviceDeliveryDTO);
        if (serviceDeliveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceDeliveryDTO result = serviceDeliveryService.save(serviceDeliveryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDeliveryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-deliveries} : get all the serviceDeliveries.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceDeliveries in body.
     */
    @GetMapping("/service-deliveries")
    public ResponseEntity<List<ServiceDeliveryDTO>> getAllServiceDeliveries(ServiceDeliveryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceDeliveries by criteria: {}", criteria);
        Page<ServiceDeliveryDTO> page = serviceDeliveryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-deliveries/count} : count all the serviceDeliveries.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-deliveries/count")
    public ResponseEntity<Long> countServiceDeliveries(ServiceDeliveryCriteria criteria) {
        log.debug("REST request to count ServiceDeliveries by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceDeliveryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-deliveries/:id} : get the "id" serviceDelivery.
     *
     * @param id the id of the serviceDeliveryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDeliveryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-deliveries/{id}")
    public ResponseEntity<ServiceDeliveryDTO> getServiceDelivery(@PathVariable Long id) {
        log.debug("REST request to get ServiceDelivery : {}", id);
        Optional<ServiceDeliveryDTO> serviceDeliveryDTO = serviceDeliveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceDeliveryDTO);
    }

    /**
     * {@code DELETE  /service-deliveries/:id} : delete the "id" serviceDelivery.
     *
     * @param id the id of the serviceDeliveryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-deliveries/{id}")
    public ResponseEntity<Void> deleteServiceDelivery(@PathVariable Long id) {
        log.debug("REST request to delete ServiceDelivery : {}", id);
        serviceDeliveryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
