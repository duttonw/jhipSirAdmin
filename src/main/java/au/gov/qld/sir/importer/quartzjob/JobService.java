package au.gov.qld.sir.importer.quartzjob;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private Scheduler scheduler;

	/**
	 * Trigger a job if it isn't already running
	 *
	 * @param jobName The name of the ob
	 * @return True on success false otherwise
	 */
	public Boolean triggerJob(String jobName) {
		JobKey jobKey = JobKey.jobKey(jobName, "SIRBOT");

		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			LOGGER.error(String.format("Failed to trigger job %s", jobName), e);
			return false;
		}

		return true;
	}
}
