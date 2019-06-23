package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceDeliveryFormService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormDTO;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormCriteria;
import au.gov.qld.sir.service.ServiceDeliveryFormQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceDeliveryForm}.
 */
@RestController
@RequestMapping("/api")
public class ServiceDeliveryFormResource {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryFormResource.class);

    private static final String ENTITY_NAME = "serviceDeliveryForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceDeliveryFormService serviceDeliveryFormService;

    private final ServiceDeliveryFormQueryService serviceDeliveryFormQueryService;

    public ServiceDeliveryFormResource(ServiceDeliveryFormService serviceDeliveryFormService, ServiceDeliveryFormQueryService serviceDeliveryFormQueryService) {
        this.serviceDeliveryFormService = serviceDeliveryFormService;
        this.serviceDeliveryFormQueryService = serviceDeliveryFormQueryService;
    }

    /**
     * {@code POST  /service-delivery-forms} : Create a new serviceDeliveryForm.
     *
     * @param serviceDeliveryFormDTO the serviceDeliveryFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceDeliveryFormDTO, or with status {@code 400 (Bad Request)} if the serviceDeliveryForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-delivery-forms")
    public ResponseEntity<ServiceDeliveryFormDTO> createServiceDeliveryForm(@Valid @RequestBody ServiceDeliveryFormDTO serviceDeliveryFormDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceDeliveryForm : {}", serviceDeliveryFormDTO);
        if (serviceDeliveryFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceDeliveryForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceDeliveryFormDTO result = serviceDeliveryFormService.save(serviceDeliveryFormDTO);
        return ResponseEntity.created(new URI("/api/service-delivery-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-delivery-forms} : Updates an existing serviceDeliveryForm.
     *
     * @param serviceDeliveryFormDTO the serviceDeliveryFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDeliveryFormDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDeliveryFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDeliveryFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-delivery-forms")
    public ResponseEntity<ServiceDeliveryFormDTO> updateServiceDeliveryForm(@Valid @RequestBody ServiceDeliveryFormDTO serviceDeliveryFormDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceDeliveryForm : {}", serviceDeliveryFormDTO);
        if (serviceDeliveryFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceDeliveryFormDTO result = serviceDeliveryFormService.save(serviceDeliveryFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDeliveryFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-delivery-forms} : get all the serviceDeliveryForms.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceDeliveryForms in body.
     */
    @GetMapping("/service-delivery-forms")
    public ResponseEntity<List<ServiceDeliveryFormDTO>> getAllServiceDeliveryForms(ServiceDeliveryFormCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceDeliveryForms by criteria: {}", criteria);
        Page<ServiceDeliveryFormDTO> page = serviceDeliveryFormQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-delivery-forms/count} : count all the serviceDeliveryForms.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-delivery-forms/count")
    public ResponseEntity<Long> countServiceDeliveryForms(ServiceDeliveryFormCriteria criteria) {
        log.debug("REST request to count ServiceDeliveryForms by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceDeliveryFormQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-delivery-forms/:id} : get the "id" serviceDeliveryForm.
     *
     * @param id the id of the serviceDeliveryFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDeliveryFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-delivery-forms/{id}")
    public ResponseEntity<ServiceDeliveryFormDTO> getServiceDeliveryForm(@PathVariable Long id) {
        log.debug("REST request to get ServiceDeliveryForm : {}", id);
        Optional<ServiceDeliveryFormDTO> serviceDeliveryFormDTO = serviceDeliveryFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceDeliveryFormDTO);
    }

    /**
     * {@code DELETE  /service-delivery-forms/:id} : delete the "id" serviceDeliveryForm.
     *
     * @param id the id of the serviceDeliveryFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-delivery-forms/{id}")
    public ResponseEntity<Void> deleteServiceDeliveryForm(@PathVariable Long id) {
        log.debug("REST request to delete ServiceDeliveryForm : {}", id);
        serviceDeliveryFormService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
