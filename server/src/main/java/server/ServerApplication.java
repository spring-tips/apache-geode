package server;

import common.Temp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;

@SpringBootApplication
@ComponentScan (basePackageClasses = Temp.class)
@CacheServerApplication(locators = "localhost[10334]")
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
