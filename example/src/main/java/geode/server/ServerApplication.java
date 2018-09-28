package geode.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

// spring.data.gemfire.cache.server.port = 0
@CacheServerApplication(locators = "localhost[10334]")
@SpringBootApplication
@Log4j2
public class ServerApplication {

	/*
	@EnableRedisServer
	@EnableMemcachedServer
*/
	@Configuration
	@EnableLocator
	@EnableManager(start = true)
//	@EnableCacheServer
	@Profile("locator")
	public static class LocatorManagerConfiguration {
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}


