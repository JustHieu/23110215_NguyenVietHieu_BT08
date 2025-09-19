package hieesu.vn.demospringst5;

import hieesu.vn.demospringst5.Config.StorageProperties;
import hieesu.vn.demospringst5.Service.Impl.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoSpringSt5Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSt5Application.class, args);
	}
    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args -> {
            storageService.init();
        });
    }
}
