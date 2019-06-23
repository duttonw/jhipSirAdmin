package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.DeliveryChannelService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.DeliveryChannelDTO;
import au.gov.qld.sir.service.dto.DeliveryChannelCriteria;
import au.gov.qld.sir.service.DeliveryChannelQueryService;

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
 * REST controller for managing {@link au.gov.qld.sir.domain.DeliveryChannel}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryChannelResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryChannelResource.class);

    private static final String ENTITY_NAME = "deliveryChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryChannelService deliveryChannelService;

    private final DeliveryChannelQueryService deliveryChannelQueryService;

    public DeliveryChannelResource(DeliveryChannelService deliveryChannelService, DeliveryChannelQueryService deliveryChannelQueryService) {
        this.deliveryChannelService = deliveryChannelService;
        this.deliveryChannelQueryService = deliveryChannelQueryService;
    }

    /**
     * {@code POST  /delivery-channels} : Create a new deliveryChannel.
     *
     * @param deliveryChannelDTO the deliveryChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryChannelDTO, or with status {@code 400 (Bad Request)} if the deliveryChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-channels")
    public ResponseEntity<DeliveryChannelDTO> createDeliveryChannel(@Valid @RequestBody DeliveryChannelDTO deliveryChannelDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryChannel : {}", deliveryChannelDTO);
        if (deliveryChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryChannelDTO result = deliveryChannelService.save(deliveryChannelDTO);
        return ResponseEntity.created(new URI("/api/delivery-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-channels} : Updates an existing deliveryChannel.
     *
     * @param deliveryChannelDTO the deliveryChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryChannelDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-channels")
    public ResponseEntity<DeliveryChannelDTO> updateDeliveryChannel(@Valid @RequestBody DeliveryChannelDTO deliveryChannelDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryChannel : {}", deliveryChannelDTO);
        if (deliveryChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryChannelDTO result = deliveryChannelService.save(deliveryChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-channels} : get all the deliveryChannels.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryChannels in body.
     */
    @GetMapping("/delivery-channels")
    public ResponseEntity<List<DeliveryChannelDTO>> getAllDeliveryChannels(DeliveryChannelCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DeliveryChannels by criteria: {}", criteria);
        Page<DeliveryChannelDTO> page = deliveryChannelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /delivery-channels/count} : count all the deliveryChannels.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/delivery-channels/count")
    public ResponseEntity<Long> countDeliveryChannels(DeliveryChannelCriteria criteria) {
        log.debug("REST request to count DeliveryChannels by criteria: {}", criteria);
        return ResponseEntity.ok().body(deliveryChannelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delivery-channels/:id} : get the "id" deliveryChannel.
     *
     * @param id the id of the deliveryChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-channels/{id}")
    public ResponseEntity<DeliveryChannelDTO> getDeliveryChannel(@PathVariable Long id) {
        log.debug("REST request to get DeliveryChannel : {}", id);
        Optional<DeliveryChannelDTO> deliveryChannelDTO = deliveryChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryChannelDTO);
    }

    /**
     * {@code DELETE  /delivery-channels/:id} : delete the "id" deliveryChannel.
     *
     * @param id the id of the deliveryChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-channels/{id}")
    public ResponseEntity<Void> deleteDeliveryChannel(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryChannel : {}", id);
        deliveryChannelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
