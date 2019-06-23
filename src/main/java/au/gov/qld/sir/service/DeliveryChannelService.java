package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.repository.DeliveryChannelRepository;
import au.gov.qld.sir.service.dto.DeliveryChannelDTO;
import au.gov.qld.sir.service.mapper.DeliveryChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeliveryChannel}.
 */
@Service
@Transactional
public class DeliveryChannelService {

    private final Logger log = LoggerFactory.getLogger(DeliveryChannelService.class);

    private final DeliveryChannelRepository deliveryChannelRepository;

    private final DeliveryChannelMapper deliveryChannelMapper;

    public DeliveryChannelService(DeliveryChannelRepository deliveryChannelRepository, DeliveryChannelMapper deliveryChannelMapper) {
        this.deliveryChannelRepository = deliveryChannelRepository;
        this.deliveryChannelMapper = deliveryChannelMapper;
    }

    /**
     * Save a deliveryChannel.
     *
     * @param deliveryChannelDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryChannelDTO save(DeliveryChannelDTO deliveryChannelDTO) {
        log.debug("Request to save DeliveryChannel : {}", deliveryChannelDTO);
        DeliveryChannel deliveryChannel = deliveryChannelMapper.toEntity(deliveryChannelDTO);
        deliveryChannel = deliveryChannelRepository.save(deliveryChannel);
        return deliveryChannelMapper.toDto(deliveryChannel);
    }

    /**
     * Get all the deliveryChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryChannelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryChannels");
        return deliveryChannelRepository.findAll(pageable)
            .map(deliveryChannelMapper::toDto);
    }


    /**
     * Get one deliveryChannel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeliveryChannelDTO> findOne(Long id) {
        log.debug("Request to get DeliveryChannel : {}", id);
        return deliveryChannelRepository.findById(id)
            .map(deliveryChannelMapper::toDto);
    }

    /**
     * Delete the deliveryChannel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeliveryChannel : {}", id);
        deliveryChannelRepository.deleteById(id);
    }
}
