package locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

@SpringBootApplication
@CacheServerApplication(locators = "localhost[10334]")
public class LocatorApplication {

	@Configuration
	@EnableLocator
	@EnableManager(start = true)
	public static class LocatorManagerConfiguration {
	}

	public static void main(String[] args) {
		SpringApplication.run( LocatorApplication.class, args);
	}
}
