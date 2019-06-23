package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceTagService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceTagDTO;
import au.gov.qld.sir.service.dto.ServiceTagCriteria;
import au.gov.qld.sir.service.ServiceTagQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceTag}.
 */
@RestController
@RequestMapping("/api")
public class ServiceTagResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTagResource.class);

    private static final String ENTITY_NAME = "serviceTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceTagService serviceTagService;

    private final ServiceTagQueryService serviceTagQueryService;

    public ServiceTagResource(ServiceTagService serviceTagService, ServiceTagQueryService serviceTagQueryService) {
        this.serviceTagService = serviceTagService;
        this.serviceTagQueryService = serviceTagQueryService;
    }

    /**
     * {@code POST  /service-tags} : Create a new serviceTag.
     *
     * @param serviceTagDTO the serviceTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceTagDTO, or with status {@code 400 (Bad Request)} if the serviceTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-tags")
    public ResponseEntity<ServiceTagDTO> createServiceTag(@Valid @RequestBody ServiceTagDTO serviceTagDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceTag : {}", serviceTagDTO);
        if (serviceTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceTagDTO result = serviceTagService.save(serviceTagDTO);
        return ResponseEntity.created(new URI("/api/service-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-tags} : Updates an existing serviceTag.
     *
     * @param serviceTagDTO the serviceTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceTagDTO,
     * or with status {@code 400 (Bad Request)} if the serviceTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-tags")
    public ResponseEntity<ServiceTagDTO> updateServiceTag(@Valid @RequestBody ServiceTagDTO serviceTagDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceTag : {}", serviceTagDTO);
        if (serviceTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceTagDTO result = serviceTagService.save(serviceTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-tags} : get all the serviceTags.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceTags in body.
     */
    @GetMapping("/service-tags")
    public ResponseEntity<List<ServiceTagDTO>> getAllServiceTags(ServiceTagCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceTags by criteria: {}", criteria);
        Page<ServiceTagDTO> page = serviceTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-tags/count} : count all the serviceTags.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-tags/count")
    public ResponseEntity<Long> countServiceTags(ServiceTagCriteria criteria) {
        log.debug("REST request to count ServiceTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-tags/:id} : get the "id" serviceTag.
     *
     * @param id the id of the serviceTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-tags/{id}")
    public ResponseEntity<ServiceTagDTO> getServiceTag(@PathVariable Long id) {
        log.debug("REST request to get ServiceTag : {}", id);
        Optional<ServiceTagDTO> serviceTagDTO = serviceTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceTagDTO);
    }

    /**
     * {@code DELETE  /service-tags/:id} : delete the "id" serviceTag.
     *
     * @param id the id of the serviceTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-tags/{id}")
    public ResponseEntity<Void> deleteServiceTag(@PathVariable Long id) {
        log.debug("REST request to delete ServiceTag : {}", id);
        serviceTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
