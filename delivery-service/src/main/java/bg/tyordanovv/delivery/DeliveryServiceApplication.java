package bg.tyordanovv.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DeliveryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceApplication.class, args);
    }
//
//    @Bean
//    public WebClient webClient(){
//        return WebClient.builder().build();
//    }
}
