package geode.cq;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import org.apache.geode.cache.query.CqEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableClusterConfiguration
@Log4j2
public class CqApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CqApplication.class, args);
		new Scanner(System.in).next();
	}

	private int count = 0;

	@ContinuousQuery(name = "NewTempsCq", query = "SELECT * FROM /temps")
	public void onNewTemp(CqEvent event) {

		Optional.ofNullable(event)
			.map(CqEvent::getNewValue)
			.ifPresent(temp -> log.info("New Temp [{}] is [{}]", ++count, temp));
	}
}
