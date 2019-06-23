package au.gov.qld.sir.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, au.gov.qld.sir.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, au.gov.qld.sir.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, au.gov.qld.sir.domain.User.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Authority.class.getName());
            createCache(cm, au.gov.qld.sir.domain.User.class.getName() + ".authorities");
            createCache(cm, au.gov.qld.sir.domain.AgencySupportRoleContextType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.AgencySupportRoleContextType.class.getName() + ".agencySupportRoles");
            createCache(cm, au.gov.qld.sir.domain.ServiceSupportRoleContextType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceSupportRoleContextType.class.getName() + ".serviceSupportRoles");
            createCache(cm, au.gov.qld.sir.domain.Agency.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Agency.class.getName() + ".agencySupportRoles");
            createCache(cm, au.gov.qld.sir.domain.Agency.class.getName() + ".integrationMappings");
            createCache(cm, au.gov.qld.sir.domain.Agency.class.getName() + ".locations");
            createCache(cm, au.gov.qld.sir.domain.Agency.class.getName() + ".serviceDeliveryOrganisations");
            createCache(cm, au.gov.qld.sir.domain.AgencySupportRole.class.getName());
            createCache(cm, au.gov.qld.sir.domain.AgencyType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.AgencyType.class.getName() + ".agencies");
            createCache(cm, au.gov.qld.sir.domain.Application.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Application.class.getName() + ".applicationServiceOverrides");
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverride.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverride.class.getName() + ".applicationServiceOverrideAttributes");
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverride.class.getName() + ".applicationServiceOverrideTagItems");
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverrideTag.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverrideTag.class.getName() + ".applicationServiceOverrideTags");
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverrideTag.class.getName() + ".applicationServiceOverrideTagItems");
            createCache(cm, au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems.class.getName());
            createCache(cm, au.gov.qld.sir.domain.AvailabilityHours.class.getName());
            createCache(cm, au.gov.qld.sir.domain.AvailabilityHours.class.getName() + ".deliveryChannels");
            createCache(cm, au.gov.qld.sir.domain.AvailabilityHours.class.getName() + ".locations");
            createCache(cm, au.gov.qld.sir.domain.AvailabilityHours.class.getName() + ".openingHoursSpecifications");
            createCache(cm, au.gov.qld.sir.domain.Category.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Category.class.getName() + ".serviceEvidences");
            createCache(cm, au.gov.qld.sir.domain.Category.class.getName() + ".serviceGroups");
            createCache(cm, au.gov.qld.sir.domain.CategoryType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.CategoryType.class.getName() + ".serviceGroups");
            createCache(cm, au.gov.qld.sir.domain.DeliveryChannel.class.getName());
            createCache(cm, au.gov.qld.sir.domain.IntegrationMapping.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Location.class.getName());
            createCache(cm, au.gov.qld.sir.domain.Location.class.getName() + ".deliveryChannels");
            createCache(cm, au.gov.qld.sir.domain.Location.class.getName() + ".locationAddresses");
            createCache(cm, au.gov.qld.sir.domain.Location.class.getName() + ".locationEmails");
            createCache(cm, au.gov.qld.sir.domain.Location.class.getName() + ".locationPhones");
            createCache(cm, au.gov.qld.sir.domain.LocationAddress.class.getName());
            createCache(cm, au.gov.qld.sir.domain.LocationEmail.class.getName());
            createCache(cm, au.gov.qld.sir.domain.LocationPhone.class.getName());
            createCache(cm, au.gov.qld.sir.domain.LocationType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.LocationType.class.getName() + ".locations");
            createCache(cm, au.gov.qld.sir.domain.OpeningHoursSpecification.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".applicationServiceOverrides");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".integrationMappings");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceRecords");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceDeliveries");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceDeliveryForms");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceDescriptions");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceEvents");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceEvidences");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceGroups");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceNames");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".fromServices");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".toServices");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceSupportRoles");
            createCache(cm, au.gov.qld.sir.domain.ServiceRecord.class.getName() + ".serviceTagItems");
            createCache(cm, au.gov.qld.sir.domain.ServiceDelivery.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceDelivery.class.getName() + ".deliveryChannels");
            createCache(cm, au.gov.qld.sir.domain.ServiceDeliveryForm.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceDeliveryOrganisation.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceDeliveryOrganisation.class.getName() + ".serviceRecords");
            createCache(cm, au.gov.qld.sir.domain.ServiceDescription.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceEvent.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceEventType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceEventType.class.getName() + ".serviceEvents");
            createCache(cm, au.gov.qld.sir.domain.ServiceEvidence.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceFranchise.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceFranchise.class.getName() + ".serviceRecords");
            createCache(cm, au.gov.qld.sir.domain.ServiceGroup.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceName.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceRelationship.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceRoleType.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceRoleType.class.getName() + ".agencySupportRoles");
            createCache(cm, au.gov.qld.sir.domain.ServiceRoleType.class.getName() + ".serviceSupportRoles");
            createCache(cm, au.gov.qld.sir.domain.ServiceSupportRole.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceTag.class.getName());
            createCache(cm, au.gov.qld.sir.domain.ServiceTag.class.getName() + ".serviceTags");
            createCache(cm, au.gov.qld.sir.domain.ServiceTag.class.getName() + ".serviceTagItems");
            createCache(cm, au.gov.qld.sir.domain.ServiceTagItems.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
