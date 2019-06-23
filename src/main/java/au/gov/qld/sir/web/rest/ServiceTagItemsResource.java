package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceTagItemsService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceTagItemsDTO;
import au.gov.qld.sir.service.dto.ServiceTagItemsCriteria;
import au.gov.qld.sir.service.ServiceTagItemsQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceTagItems}.
 */
@RestController
@RequestMapping("/api")
public class ServiceTagItemsResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTagItemsResource.class);

    private static final String ENTITY_NAME = "serviceTagItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceTagItemsService serviceTagItemsService;

    private final ServiceTagItemsQueryService serviceTagItemsQueryService;

    public ServiceTagItemsResource(ServiceTagItemsService serviceTagItemsService, ServiceTagItemsQueryService serviceTagItemsQueryService) {
        this.serviceTagItemsService = serviceTagItemsService;
        this.serviceTagItemsQueryService = serviceTagItemsQueryService;
    }

    /**
     * {@code POST  /service-tag-items} : Create a new serviceTagItems.
     *
     * @param serviceTagItemsDTO the serviceTagItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceTagItemsDTO, or with status {@code 400 (Bad Request)} if the serviceTagItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-tag-items")
    public ResponseEntity<ServiceTagItemsDTO> createServiceTagItems(@Valid @RequestBody ServiceTagItemsDTO serviceTagItemsDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceTagItems : {}", serviceTagItemsDTO);
        if (serviceTagItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceTagItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceTagItemsDTO result = serviceTagItemsService.save(serviceTagItemsDTO);
        return ResponseEntity.created(new URI("/api/service-tag-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-tag-items} : Updates an existing serviceTagItems.
     *
     * @param serviceTagItemsDTO the serviceTagItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceTagItemsDTO,
     * or with status {@code 400 (Bad Request)} if the serviceTagItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceTagItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-tag-items")
    public ResponseEntity<ServiceTagItemsDTO> updateServiceTagItems(@Valid @RequestBody ServiceTagItemsDTO serviceTagItemsDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceTagItems : {}", serviceTagItemsDTO);
        if (serviceTagItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceTagItemsDTO result = serviceTagItemsService.save(serviceTagItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceTagItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-tag-items} : get all the serviceTagItems.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceTagItems in body.
     */
    @GetMapping("/service-tag-items")
    public ResponseEntity<List<ServiceTagItemsDTO>> getAllServiceTagItems(ServiceTagItemsCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceTagItems by criteria: {}", criteria);
        Page<ServiceTagItemsDTO> page = serviceTagItemsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-tag-items/count} : count all the serviceTagItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-tag-items/count")
    public ResponseEntity<Long> countServiceTagItems(ServiceTagItemsCriteria criteria) {
        log.debug("REST request to count ServiceTagItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceTagItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-tag-items/:id} : get the "id" serviceTagItems.
     *
     * @param id the id of the serviceTagItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceTagItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-tag-items/{id}")
    public ResponseEntity<ServiceTagItemsDTO> getServiceTagItems(@PathVariable Long id) {
        log.debug("REST request to get ServiceTagItems : {}", id);
        Optional<ServiceTagItemsDTO> serviceTagItemsDTO = serviceTagItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceTagItemsDTO);
    }

    /**
     * {@code DELETE  /service-tag-items/:id} : delete the "id" serviceTagItems.
     *
     * @param id the id of the serviceTagItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-tag-items/{id}")
    public ResponseEntity<Void> deleteServiceTagItems(@PathVariable Long id) {
        log.debug("REST request to delete ServiceTagItems : {}", id);
        serviceTagItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
