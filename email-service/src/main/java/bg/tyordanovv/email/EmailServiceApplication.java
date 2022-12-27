package bg.tyordanovv.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("bg.tyordanovv")
public class EmailServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }
}
