package com.nirav;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchExecutor {
	public static void main(String[] args) throws Throwable {
		
		
	}
	
	private void run() {
		String[] springConfig = { "spring/batch/job/job-partion.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("partitionJob");
		
	}
}
