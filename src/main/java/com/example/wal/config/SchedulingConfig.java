package com.example.wal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
public class SchedulingConfig implements SchedulingConfigurer, AsyncConfigurer {

    private final static int THREAD_MAX_POOL_SIZE = 10;

    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(THREAD_MAX_POOL_SIZE);
        executor.setThreadNamePrefix("scheduler-thread-");
        executor.setThreadGroupName("scheduler thread pool");
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.threadPoolTaskScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler());
    }
}
