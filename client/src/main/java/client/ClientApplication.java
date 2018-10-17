package client;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import common.Temp;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableClusterConfiguration // will create 'temps' for me
@EnableEntityDefinedRegions(basePackageClasses = Temp.class)
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

interface TempRepository extends CrudRepository<Temp, Long> {
}

@Log4j2
@Component
class Runner implements ApplicationListener<ApplicationReadyEvent> {

	private final TempRepository tempRepository;
	private final AverageTemperatureFunctionExecution execution;

	Runner(TempRepository tempRepository, AverageTemperatureFunctionExecution execution) {
		this.tempRepository = tempRepository;
		this.execution = execution;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		// delete
		this.tempRepository
			.findAll()
			.forEach(this.tempRepository::delete);

		// insert
		int start = (int) (Math.random() * 100);
		for (int i = start; i < start + 100; i++) {
			this.insertRecord();
		}

		// query
		Object averageTemperature = this.execution.averageTemperature();
		log.info("averageTemperature: " + averageTemperature.toString());

	}

	private String randomCity() {
		String[] cities = "Toronto,San Francisco,New York".split(",");
		return cities[new Random().nextInt(cities.length)];
	}

	private void insertRecord() {
		Temp tmp = new Temp(new Random().nextLong(),
			new Random().nextDouble() * 110, this.randomCity());
		Temp saved = this.tempRepository.save(tmp);
		Long inCache = this.tempRepository.findById(saved.getId()).map(Temp::getId).orElse(null);
		Assert.isTrue(saved.getId().equals(inCache), "roundtrip should work");
	}
}
