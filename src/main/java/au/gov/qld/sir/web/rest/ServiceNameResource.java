package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceNameService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceNameDTO;
import au.gov.qld.sir.service.dto.ServiceNameCriteria;
import au.gov.qld.sir.service.ServiceNameQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceName}.
 */
@RestController
@RequestMapping("/api")
public class ServiceNameResource {

    private final Logger log = LoggerFactory.getLogger(ServiceNameResource.class);

    private static final String ENTITY_NAME = "serviceName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceNameService serviceNameService;

    private final ServiceNameQueryService serviceNameQueryService;

    public ServiceNameResource(ServiceNameService serviceNameService, ServiceNameQueryService serviceNameQueryService) {
        this.serviceNameService = serviceNameService;
        this.serviceNameQueryService = serviceNameQueryService;
    }

    /**
     * {@code POST  /service-names} : Create a new serviceName.
     *
     * @param serviceNameDTO the serviceNameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceNameDTO, or with status {@code 400 (Bad Request)} if the serviceName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-names")
    public ResponseEntity<ServiceNameDTO> createServiceName(@Valid @RequestBody ServiceNameDTO serviceNameDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceName : {}", serviceNameDTO);
        if (serviceNameDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceNameDTO result = serviceNameService.save(serviceNameDTO);
        return ResponseEntity.created(new URI("/api/service-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-names} : Updates an existing serviceName.
     *
     * @param serviceNameDTO the serviceNameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceNameDTO,
     * or with status {@code 400 (Bad Request)} if the serviceNameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceNameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-names")
    public ResponseEntity<ServiceNameDTO> updateServiceName(@Valid @RequestBody ServiceNameDTO serviceNameDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceName : {}", serviceNameDTO);
        if (serviceNameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceNameDTO result = serviceNameService.save(serviceNameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceNameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-names} : get all the serviceNames.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceNames in body.
     */
    @GetMapping("/service-names")
    public ResponseEntity<List<ServiceNameDTO>> getAllServiceNames(ServiceNameCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceNames by criteria: {}", criteria);
        Page<ServiceNameDTO> page = serviceNameQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-names/count} : count all the serviceNames.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-names/count")
    public ResponseEntity<Long> countServiceNames(ServiceNameCriteria criteria) {
        log.debug("REST request to count ServiceNames by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceNameQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-names/:id} : get the "id" serviceName.
     *
     * @param id the id of the serviceNameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceNameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-names/{id}")
    public ResponseEntity<ServiceNameDTO> getServiceName(@PathVariable Long id) {
        log.debug("REST request to get ServiceName : {}", id);
        Optional<ServiceNameDTO> serviceNameDTO = serviceNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceNameDTO);
    }

    /**
     * {@code DELETE  /service-names/:id} : delete the "id" serviceName.
     *
     * @param id the id of the serviceNameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-names/{id}")
    public ResponseEntity<Void> deleteServiceName(@PathVariable Long id) {
        log.debug("REST request to delete ServiceName : {}", id);
        serviceNameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
