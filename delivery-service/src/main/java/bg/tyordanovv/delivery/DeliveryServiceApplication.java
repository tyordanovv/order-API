package bg.tyordanovv.delivery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
public class DeliveryServiceApplication {
    private final Integer threadPoolSize;
    private final Integer taskQueueSize;

    @Autowired
    public DeliveryServiceApplication(
            @Value("${app.threadPoolSize:10}") Integer threadPoolSize,
            @Value("${app.taskQueueSize:100}") Integer taskQueueSize
    ) {
        this.threadPoolSize = threadPoolSize;
        this.taskQueueSize = taskQueueSize;
    }

    @Bean
    public Scheduler jdbcScheduler() {
        log.info("Creates a jdbcScheduler with thread pool size = {}", threadPoolSize);
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "jdbc-pool");
    }
    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceApplication.class, args);
    }
}
