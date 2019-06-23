package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceRecordService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceRecordDTO;
import au.gov.qld.sir.service.dto.ServiceRecordCriteria;
import au.gov.qld.sir.service.ServiceRecordQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceRecord}.
 */
@RestController
@RequestMapping("/api")
public class ServiceRecordResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRecordResource.class);

    private static final String ENTITY_NAME = "serviceRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRecordService serviceRecordService;

    private final ServiceRecordQueryService serviceRecordQueryService;

    public ServiceRecordResource(ServiceRecordService serviceRecordService, ServiceRecordQueryService serviceRecordQueryService) {
        this.serviceRecordService = serviceRecordService;
        this.serviceRecordQueryService = serviceRecordQueryService;
    }

    /**
     * {@code POST  /service-records} : Create a new serviceRecord.
     *
     * @param serviceRecordDTO the serviceRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRecordDTO, or with status {@code 400 (Bad Request)} if the serviceRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-records")
    public ResponseEntity<ServiceRecordDTO> createServiceRecord(@Valid @RequestBody ServiceRecordDTO serviceRecordDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceRecord : {}", serviceRecordDTO);
        if (serviceRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRecordDTO result = serviceRecordService.save(serviceRecordDTO);
        return ResponseEntity.created(new URI("/api/service-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-records} : Updates an existing serviceRecord.
     *
     * @param serviceRecordDTO the serviceRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRecordDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-records")
    public ResponseEntity<ServiceRecordDTO> updateServiceRecord(@Valid @RequestBody ServiceRecordDTO serviceRecordDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceRecord : {}", serviceRecordDTO);
        if (serviceRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceRecordDTO result = serviceRecordService.save(serviceRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-records} : get all the serviceRecords.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRecords in body.
     */
    @GetMapping("/service-records")
    public ResponseEntity<List<ServiceRecordDTO>> getAllServiceRecords(ServiceRecordCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceRecords by criteria: {}", criteria);
        Page<ServiceRecordDTO> page = serviceRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-records/count} : count all the serviceRecords.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-records/count")
    public ResponseEntity<Long> countServiceRecords(ServiceRecordCriteria criteria) {
        log.debug("REST request to count ServiceRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceRecordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-records/:id} : get the "id" serviceRecord.
     *
     * @param id the id of the serviceRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-records/{id}")
    public ResponseEntity<ServiceRecordDTO> getServiceRecord(@PathVariable Long id) {
        log.debug("REST request to get ServiceRecord : {}", id);
        Optional<ServiceRecordDTO> serviceRecordDTO = serviceRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRecordDTO);
    }

    /**
     * {@code DELETE  /service-records/:id} : delete the "id" serviceRecord.
     *
     * @param id the id of the serviceRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-records/{id}")
    public ResponseEntity<Void> deleteServiceRecord(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRecord : {}", id);
        serviceRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
