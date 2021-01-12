package biz.digitalhouse.integration.v3.configs;

import biz.digitalhouse.integration.v3.services.jobs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Vitalii Babenko
 *         on 19.04.2016.
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource dataSource;

    @Bean
    public JobDetailFactoryBean refundJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(RefundJob.class);
        jobDetailFactory.setGroup("INTEGRATION_SERVICE");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean refundJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(refundJob().getObject());
        cronTriggerFactoryBean.setGroup("INTEGRATION_SERVICE");
        cronTriggerFactoryBean.setCronExpression("*/1 * * * * ?");

        return cronTriggerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean winJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(WinJob.class);
        jobDetailFactory.setGroup("INTEGRATION_SERVICE");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean winJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(winJob().getObject());
        cronTriggerFactoryBean.setGroup("INTEGRATION_SERVICE");
        cronTriggerFactoryBean.setCronExpression("*/1 * * * * ?");

        return cronTriggerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean jackpotWinJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(JackpotWinJob.class);
        jobDetailFactory.setGroup("INTEGRATION_SERVICE");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean jackpotWinJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jackpotWinJob().getObject());
        cronTriggerFactoryBean.setGroup("INTEGRATION_SERVICE");
        cronTriggerFactoryBean.setCronExpression("*/1 * * * * ?");

        return cronTriggerFactoryBean;
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {

        Properties properties = new Properties();
        // Configure Main Scheduler Properties
        properties.put("org.quartz.scheduler.instanceName", "ClusteredScheduler");
        properties.put("org.quartz.scheduler.instanceId", "AUTO");
        // Configure ThreadPool
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "10");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        // Configure JobStore
        properties.put("org.quartz.jobStore.misfireThreshold", "60000");
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        properties.put("org.quartz.jobStore.useProperties", "true");
        properties.put("org.quartz.jobStore.dataSource", "quartz");
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_INTEGRATION_SERVICE_");
        properties.put("org.quartz.jobStore.isClustered", "true");
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "20000");

        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setApplicationContext(applicationContext);
        bean.setOverwriteExistingJobs(true);
        bean.setTriggers(
                refundJobTrigger().getObject(),
                winJobTrigger().getObject(),
                jackpotWinJobTrigger().getObject()
        );
        bean.setDataSource(dataSource);
        bean.setQuartzProperties(properties);

        // custom job factory of spring with DI support for @Autowired!
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        bean.setJobFactory(jobFactory);

        return bean;
    }
}
