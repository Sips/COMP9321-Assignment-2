package edu.unsw.comp9321.assign2.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import edu.unsw.comp9321.assign2.notifications.EmailNotificationJob;

public class EmailNotificationListener implements ServletContextListener {

	private Scheduler sched;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Stopping the Application");
		try{
			sched.shutdown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Starting The Application");
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(EmailNotificationJob.class)
					.withIdentity("job1", "group1").build();

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("job1", "group1")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(10).repeatForever())
					.build();

			sched.scheduleJob(job, trigger);
			
			sched.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
