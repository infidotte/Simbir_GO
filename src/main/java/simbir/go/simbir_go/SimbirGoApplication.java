package simbir.go.simbir_go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource("classpath:application.properties")
@EntityScan("simbir.go.simbir_go.Entity")
@EnableJpaRepositories("simbir.go.simbir_go.Repository")
@EnableTransactionManagement
@SpringBootApplication
public class SimbirGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimbirGoApplication.class, args);
    }

}
