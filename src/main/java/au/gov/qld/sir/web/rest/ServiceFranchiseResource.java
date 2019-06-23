package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.ServiceFranchiseService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.ServiceFranchiseDTO;
import au.gov.qld.sir.service.dto.ServiceFranchiseCriteria;
import au.gov.qld.sir.service.ServiceFranchiseQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.ServiceFranchise}.
 */
@RestController
@RequestMapping("/api")
public class ServiceFranchiseResource {

    private final Logger log = LoggerFactory.getLogger(ServiceFranchiseResource.class);

    private static final String ENTITY_NAME = "serviceFranchise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceFranchiseService serviceFranchiseService;

    private final ServiceFranchiseQueryService serviceFranchiseQueryService;

    public ServiceFranchiseResource(ServiceFranchiseService serviceFranchiseService, ServiceFranchiseQueryService serviceFranchiseQueryService) {
        this.serviceFranchiseService = serviceFranchiseService;
        this.serviceFranchiseQueryService = serviceFranchiseQueryService;
    }

    /**
     * {@code POST  /service-franchises} : Create a new serviceFranchise.
     *
     * @param serviceFranchiseDTO the serviceFranchiseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceFranchiseDTO, or with status {@code 400 (Bad Request)} if the serviceFranchise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-franchises")
    public ResponseEntity<ServiceFranchiseDTO> createServiceFranchise(@Valid @RequestBody ServiceFranchiseDTO serviceFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceFranchise : {}", serviceFranchiseDTO);
        if (serviceFranchiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceFranchise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceFranchiseDTO result = serviceFranchiseService.save(serviceFranchiseDTO);
        return ResponseEntity.created(new URI("/api/service-franchises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-franchises} : Updates an existing serviceFranchise.
     *
     * @param serviceFranchiseDTO the serviceFranchiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceFranchiseDTO,
     * or with status {@code 400 (Bad Request)} if the serviceFranchiseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceFranchiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-franchises")
    public ResponseEntity<ServiceFranchiseDTO> updateServiceFranchise(@Valid @RequestBody ServiceFranchiseDTO serviceFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceFranchise : {}", serviceFranchiseDTO);
        if (serviceFranchiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceFranchiseDTO result = serviceFranchiseService.save(serviceFranchiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceFranchiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-franchises} : get all the serviceFranchises.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceFranchises in body.
     */
    @GetMapping("/service-franchises")
    public ResponseEntity<List<ServiceFranchiseDTO>> getAllServiceFranchises(ServiceFranchiseCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceFranchises by criteria: {}", criteria);
        Page<ServiceFranchiseDTO> page = serviceFranchiseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-franchises/count} : count all the serviceFranchises.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-franchises/count")
    public ResponseEntity<Long> countServiceFranchises(ServiceFranchiseCriteria criteria) {
        log.debug("REST request to count ServiceFranchises by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceFranchiseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-franchises/:id} : get the "id" serviceFranchise.
     *
     * @param id the id of the serviceFranchiseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceFranchiseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-franchises/{id}")
    public ResponseEntity<ServiceFranchiseDTO> getServiceFranchise(@PathVariable Long id) {
        log.debug("REST request to get ServiceFranchise : {}", id);
        Optional<ServiceFranchiseDTO> serviceFranchiseDTO = serviceFranchiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceFranchiseDTO);
    }

    /**
     * {@code DELETE  /service-franchises/:id} : delete the "id" serviceFranchise.
     *
     * @param id the id of the serviceFranchiseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-franchises/{id}")
    public ResponseEntity<Void> deleteServiceFranchise(@PathVariable Long id) {
        log.debug("REST request to delete ServiceFranchise : {}", id);
        serviceFranchiseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
