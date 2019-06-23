import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agency-support-role-context-type',
        loadChildren:
          './agency-support-role-context-type/agency-support-role-context-type.module#JhipSirAdminAgencySupportRoleContextTypeModule'
      },
      {
        path: 'service-support-role-context-type',
        loadChildren:
          './service-support-role-context-type/service-support-role-context-type.module#JhipSirAdminServiceSupportRoleContextTypeModule'
      },
      {
        path: 'agency',
        loadChildren: './agency/agency.module#JhipSirAdminAgencyModule'
      },
      {
        path: 'agency-support-role',
        loadChildren: './agency-support-role/agency-support-role.module#JhipSirAdminAgencySupportRoleModule'
      },
      {
        path: 'agency-type',
        loadChildren: './agency-type/agency-type.module#JhipSirAdminAgencyTypeModule'
      },
      {
        path: 'application',
        loadChildren: './application/application.module#JhipSirAdminApplicationModule'
      },
      {
        path: 'application-service-override',
        loadChildren: './application-service-override/application-service-override.module#JhipSirAdminApplicationServiceOverrideModule'
      },
      {
        path: 'application-service-override-attribute',
        loadChildren:
          './application-service-override-attribute/application-service-override-attribute.module#JhipSirAdminApplicationServiceOverrideAttributeModule'
      },
      {
        path: 'application-service-override-tag',
        loadChildren:
          './application-service-override-tag/application-service-override-tag.module#JhipSirAdminApplicationServiceOverrideTagModule'
      },
      {
        path: 'application-service-override-tag-items',
        loadChildren:
          './application-service-override-tag-items/application-service-override-tag-items.module#JhipSirAdminApplicationServiceOverrideTagItemsModule'
      },
      {
        path: 'availability-hours',
        loadChildren: './availability-hours/availability-hours.module#JhipSirAdminAvailabilityHoursModule'
      },
      {
        path: 'category',
        loadChildren: './category/category.module#JhipSirAdminCategoryModule'
      },
      {
        path: 'category-type',
        loadChildren: './category-type/category-type.module#JhipSirAdminCategoryTypeModule'
      },
      {
        path: 'delivery-channel',
        loadChildren: './delivery-channel/delivery-channel.module#JhipSirAdminDeliveryChannelModule'
      },
      {
        path: 'integration-mapping',
        loadChildren: './integration-mapping/integration-mapping.module#JhipSirAdminIntegrationMappingModule'
      },
      {
        path: 'location',
        loadChildren: './location/location.module#JhipSirAdminLocationModule'
      },
      {
        path: 'location-address',
        loadChildren: './location-address/location-address.module#JhipSirAdminLocationAddressModule'
      },
      {
        path: 'location-email',
        loadChildren: './location-email/location-email.module#JhipSirAdminLocationEmailModule'
      },
      {
        path: 'location-phone',
        loadChildren: './location-phone/location-phone.module#JhipSirAdminLocationPhoneModule'
      },
      {
        path: 'location-type',
        loadChildren: './location-type/location-type.module#JhipSirAdminLocationTypeModule'
      },
      {
        path: 'opening-hours-specification',
        loadChildren: './opening-hours-specification/opening-hours-specification.module#JhipSirAdminOpeningHoursSpecificationModule'
      },
      {
        path: 'service-record',
        loadChildren: './service-record/service-record.module#JhipSirAdminServiceRecordModule'
      },
      {
        path: 'service-delivery',
        loadChildren: './service-delivery/service-delivery.module#JhipSirAdminServiceDeliveryModule'
      },
      {
        path: 'service-delivery-form',
        loadChildren: './service-delivery-form/service-delivery-form.module#JhipSirAdminServiceDeliveryFormModule'
      },
      {
        path: 'service-delivery-organisation',
        loadChildren: './service-delivery-organisation/service-delivery-organisation.module#JhipSirAdminServiceDeliveryOrganisationModule'
      },
      {
        path: 'service-description',
        loadChildren: './service-description/service-description.module#JhipSirAdminServiceDescriptionModule'
      },
      {
        path: 'service-event',
        loadChildren: './service-event/service-event.module#JhipSirAdminServiceEventModule'
      },
      {
        path: 'service-event-type',
        loadChildren: './service-event-type/service-event-type.module#JhipSirAdminServiceEventTypeModule'
      },
      {
        path: 'service-evidence',
        loadChildren: './service-evidence/service-evidence.module#JhipSirAdminServiceEvidenceModule'
      },
      {
        path: 'service-franchise',
        loadChildren: './service-franchise/service-franchise.module#JhipSirAdminServiceFranchiseModule'
      },
      {
        path: 'service-group',
        loadChildren: './service-group/service-group.module#JhipSirAdminServiceGroupModule'
      },
      {
        path: 'service-name',
        loadChildren: './service-name/service-name.module#JhipSirAdminServiceNameModule'
      },
      {
        path: 'service-relationship',
        loadChildren: './service-relationship/service-relationship.module#JhipSirAdminServiceRelationshipModule'
      },
      {
        path: 'service-role-type',
        loadChildren: './service-role-type/service-role-type.module#JhipSirAdminServiceRoleTypeModule'
      },
      {
        path: 'service-support-role',
        loadChildren: './service-support-role/service-support-role.module#JhipSirAdminServiceSupportRoleModule'
      },
      {
        path: 'service-tag',
        loadChildren: './service-tag/service-tag.module#JhipSirAdminServiceTagModule'
      },
      {
        path: 'service-tag-items',
        loadChildren: './service-tag-items/service-tag-items.module#JhipSirAdminServiceTagItemsModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminEntityModule {}
