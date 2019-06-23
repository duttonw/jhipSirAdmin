package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceRecordRepository;
import au.gov.qld.sir.service.dto.ServiceRecordDTO;
import au.gov.qld.sir.service.mapper.ServiceRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceRecord}.
 */
@Service
@Transactional
public class ServiceRecordService {

    private final Logger log = LoggerFactory.getLogger(ServiceRecordService.class);

    private final ServiceRecordRepository serviceRecordRepository;

    private final ServiceRecordMapper serviceRecordMapper;

    public ServiceRecordService(ServiceRecordRepository serviceRecordRepository, ServiceRecordMapper serviceRecordMapper) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.serviceRecordMapper = serviceRecordMapper;
    }

    /**
     * Save a serviceRecord.
     *
     * @param serviceRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRecordDTO save(ServiceRecordDTO serviceRecordDTO) {
        log.debug("Request to save ServiceRecord : {}", serviceRecordDTO);
        ServiceRecord serviceRecord = serviceRecordMapper.toEntity(serviceRecordDTO);
        serviceRecord = serviceRecordRepository.save(serviceRecord);
        return serviceRecordMapper.toDto(serviceRecord);
    }

    /**
     * Get all the serviceRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRecords");
        return serviceRecordRepository.findAll(pageable)
            .map(serviceRecordMapper::toDto);
    }


    /**
     * Get one serviceRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceRecordDTO> findOne(Long id) {
        log.debug("Request to get ServiceRecord : {}", id);
        return serviceRecordRepository.findById(id)
            .map(serviceRecordMapper::toDto);
    }

    /**
     * Delete the serviceRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceRecord : {}", id);
        serviceRecordRepository.deleteById(id);
    }
}
