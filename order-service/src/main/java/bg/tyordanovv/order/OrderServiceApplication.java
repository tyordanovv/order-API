package bg.tyordanovv.order;

//import io.swagger.v3.oas.models.ExternalDocumentation;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class OrderServiceApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

//    @Value("${api.common.version}")                 String applicationVersion;
//    @Value("${api.common.title}")                   String title;
//    @Value("${api.common.description}")             String description;
//    @Value("${api.common.termsOfService}")          String termsOfService;
//    @Value("${api.common.license}")                 String license;
//    @Value("${api.common.licenseUrl}")              String licenseUrl;
//    @Value("${api.common.externalDocDescription}")  String externalDocDescription;
//    @Value("${api.common.externalDocUrl}")          String externalDocURL;
//    @Value("${api.common.contact.name}")            String contactName;
//    @Value("${api.common.contact.url}")             String contactURL;
//    @Value("${api.common.contact.email}")           String contactEmail;
//    /**
//     * Exposes the ../swagger-ui.html
//     *
//     * @return OpenAPI documentation
//     */
//    @Bean
//    public OpenAPI myOpenAPI() {
//        Contact contact = new Contact();
//        contact.setEmail("contactEmail");
//        contact.setName("contactName");
//        contact.setUrl("contactURL");
//
//        Server localServer = new Server();
//        localServer.setUrl("http://localhost:8082");
//        localServer.setDescription("Server URL in Local environment");
//
//        Server productionServer = new Server();
//        productionServer.setUrl("https://order-api.com");
//        productionServer.setDescription("Server URL in Production environment");
//
//        License mitLicense = new License()
//                .name("license")
//                .url("licenseUrl");
//
//        Info info = new Info()
//                .title("title")
//                .contact(contact)
//                .version("applicationVersion")
//                .description("description")
//                .termsOfService("termsOfService")
//                .license(mitLicense);
//
//        return new OpenAPI()
//                .info(info)
//                .servers(List.of(localServer, productionServer));
//    }
}
