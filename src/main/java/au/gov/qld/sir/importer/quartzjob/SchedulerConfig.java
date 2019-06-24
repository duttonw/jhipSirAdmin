package au.gov.qld.sir.importer.quartzjob;

import au.gov.qld.sir.importer.quartzjob.job.GrantsQuartzJob;
import au.gov.qld.sir.importer.quartzjob.job.ImporterQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")

public class SchedulerConfig {

	@Value("${importer_api}")
	private String importerApi;

	@Value("${importer_keyword}")
	private String importerKeyword;

	@Value("${importer_scheduled}")
	private int importerScheduled;

	@Value("${grant_scheduled}")
	private int grantScheduled;

	@Value("${importer_trigger}")
	private int importerTrigger;


    @Autowired
    private Scheduler scheduler;

	public SchedulerConfig() throws Exception {

		// Nuke all quartz data in case we have corrupt data persisted
		// FIXME: This may cause issues with concurrent runs
		scheduler.clear();

		//Grants Importer
		JobKey grantsJobKey = JobKey.jobKey("grantsJobDetail", "SIRBOT");
		JobDetail grantsJobDetail = scheduler.getJobDetail(grantsJobKey);

		if (grantsJobDetail != null) {
			// Existing Trigger
			Trigger oldGrantsTrigger = scheduler.getTrigger(new TriggerKey("grantsJobTrigger", "SIRBOT"));
			TriggerBuilder tbGrants = oldGrantsTrigger.getTriggerBuilder();

			// Run every hour at second  and minute zero
			Trigger newGrantsTrigger = tbGrants.withSchedule(cronSchedule("0 0 0/" + grantScheduled + " ? * * *")).startNow().build();
			scheduler.rescheduleJob(oldGrantsTrigger.getKey(), newGrantsTrigger);
		} else {
			grantsJobKey = JobKey.jobKey("grantsJobDetail", "SIRBOT");
		}

		// * NOTE:  To run the Grants importer locally, uncomment the line below.
//		 scheduler.triggerJob(grantsJobKey);

		//importerJobDetail
		JobKey jobKey = JobKey.jobKey("importerJobDetail", "SIRBOT");
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail != null) {
			// Existing Trigger
			Trigger oldTrigger = scheduler.getTrigger(new TriggerKey("importerJobTrigger", "SIRBOT"));
			TriggerBuilder tb = oldTrigger.getTriggerBuilder();

			// // Run every hour at second zero and minute 10 to offset from grants
			Trigger newTrigger = tb.withSchedule(cronSchedule("0 10 0/" + importerScheduled +" ? * * *")).startNow().build();
			scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
		} else {
			jobKey = JobKey.jobKey("importerJobDetail", "SIRBOT");
		}

		// * NOTE: To run the basic service info importer locally, uncomment the line below.
		//scheduler.triggerJob(jobKey);
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/application.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public JobDetailFactoryBean importerJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("importApi", importerApi);
		jobDataMap.put("importKeyword", importerKeyword);
		jobDataMap.put("importTrigger", importerTrigger);
		jobDataMap.put("notificationApi", null);
		jobDataMap.put("notificationKey", null);
		return createJobDetail(ImporterQuartzJob.class, jobDataMap);
	}

	@Bean
	public JobDetailFactoryBean grantsJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("importApi", importerApi);
		jobDataMap.put("importKeyword", importerKeyword);
		jobDataMap.put("importTrigger", importerTrigger);
		jobDataMap.put("notificationApi", null);
		jobDataMap.put("notificationKey", null);
		return createJobDetail(GrantsQuartzJob.class, jobDataMap);
	}
	
	@Bean(name = "importerJobTrigger")
	public CronTriggerFactoryBean importerJobTrigger(@Qualifier("importerJobDetail") JobDetail jobDetail) {
		String scheduler = "0 10 0/" + importerScheduled +" ? * * *";
		return createCronTrigger(jobDetail, scheduler);
	}

	
	@Bean(name = "grantsJobTrigger")
	public CronTriggerFactoryBean grantsJobTrigger(@Qualifier("grantsJobDetail") JobDetail jobDetail) {
		String scheduler = "0 0 0/" + grantScheduled + " ? * * *";
		return createCronTrigger(jobDetail, scheduler);
	}

	
	private static JobDetailFactoryBean createJobDetail(Class jobClass, JobDataMap jobDataMap) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setJobDataAsMap(jobDataMap);
		factoryBean.setGroup("SIRBOT");

		// job has to be durable to be stored in DB:
		factoryBean.setDurability(false);
		return factoryBean;
	}

	/**
	 * THis method is used for creating cron triggeres in place of simple triggers.
 	 */
	private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setGroup("SIRBOT");
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
		return factoryBean;
	}
}
