package cq;

import org.apache.geode.cache.query.CqEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@EnableClusterConfiguration
public class CqApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CqApplication.class, args);
		new Scanner(System.in).next();
	}

	private int count = 0;

	@ContinuousQuery(name = "NewTempsCq",
		query = "SELECT * FROM /temps where city.contains('San')")
	public void onNewTemp(CqEvent event) {

		Optional.ofNullable(event)
			.map(CqEvent::getNewValue)
			.ifPresent(temp -> System.out.println(
				"New Temp [" + (++count) + "] is [" + temp + "]"
				));
	}
}
