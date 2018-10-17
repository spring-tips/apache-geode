package locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

import server.fn.TempAverageFunction;

@SpringBootApplication
@CacheServerApplication(locators = "localhost[10334]")
@ComponentScan(basePackageClasses = TempAverageFunction.class)
public class LocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run( LocatorApplication.class, args);
	}

	@Configuration
	@EnableLocator
	@EnableManager(start = true)
	public static class LocatorManagerConfiguration { }

}
