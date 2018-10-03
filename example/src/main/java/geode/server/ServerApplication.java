package geode.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

@SpringBootApplication
@CacheServerApplication(locators = "localhost[10334]")
@Log4j2
public class ServerApplication {

	// TODO Remember, Manager is optional and Locator is only necessary in if you want to scale up (recommended).
	@Configuration
	@EnableLocator
	@EnableManager(start = true)
//	@EnableCacheServer
//	@EnableRedisServer
//	@EnableMemcachedServer
	@Profile("locator")
	public static class LocatorManagerConfiguration {
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
