package com.myidol.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본적으로 실행을 유지하는 스레드의 수입니다. 이 경우에는 5개의 스레드가 기본적으로 유지됩니다.
        executor.setMaxPoolSize(10); // 동시에 실행할 수 있는 최대 스레드 수입니다. 이 경우에는 최대 10개의 스레드가 동시에 실행될 수 있습니다.
        executor.setQueueCapacity(100); // 작업 큐의 용량입니다. 이 경우에는 100개의 작업을 큐에 넣을 수 있습니다.
        executor.setThreadNamePrefix("AsyncExecutor-"); // 생성되는 스레드의 이름 접두사입니다. 이 경우에는 "AsyncExecutor-"로 시작하는 이름을 가진 스레드가 생성됩니다.
        executor.initialize();
        // 개선점에 대해서는, 설정 값들은 애플리케이션의 요구 사항과 리소스에 따라 조정될 수 있습니다. 예를 들어, 동시에 처리해야 하는 작업의 수가 많다면 maxPoolSize나 queueCapacity를 늘려야 할 수 있습니다. 반대로, 리소스가 제한적이라면 이러한 값들을 줄여서 리소스 사용을 최적화할 수 있습니다.
        return executor;
    }
}
