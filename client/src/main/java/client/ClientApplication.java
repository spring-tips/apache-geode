package client;

import common.Temperature;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@EnableClusterConfiguration
@EnableEntityDefinedRegions(basePackageClasses = Temperature.class)
@SpringBootApplication
public class ClientApplication {

	public static void main(String args[]) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

interface TemperatureRepository extends CrudRepository<Temperature, Long> {
}

@Log4j2
@Component
class Runner implements ApplicationListener<ApplicationReadyEvent> {

	private final TemperatureRepository temperatureRepository;
	private final AverageTemperatureFunctionInvoker invoker;

	Runner(TemperatureRepository temperatureRepository, AverageTemperatureFunctionInvoker invoker) {
		this.temperatureRepository = temperatureRepository;
		this.invoker = invoker;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent evt) {

		// reset
		this.temperatureRepository
			.findAll()
			.forEach(this.temperatureRepository::delete);

		// insert
		int randomOffset = new Random().nextInt(1000);
		for (int i = 0; i < (randomOffset + 100); i++) {
			this.insert();
		}

		// invoke function
		Collection<Double> averages = this.invoker.average();
		Double clusterAvergage = averages
			.stream()
			.collect(Collectors.averagingDouble(x -> x));

		log.info("cluster average: " + clusterAvergage);

	}

	private void insert() {
		Temperature tmp = new Temperature(
			new Random().nextLong(),
			new Random().nextDouble(),
			randomCity()
		);
		Temperature saved = this.temperatureRepository.save(tmp);
		Optional<Temperature> byId = this.temperatureRepository.findById(saved.getId());
		Long id = byId.map(Temperature::getId).orElse(null);
		Assert.isTrue(id.equals(saved.getId()), "the temperature entity should " +
			"exist in the database");
	}

	private String randomCity() {
		String cities[] = ("San Francisco,Toronto,San Ramon,San Gabriel,Los Angeles," +
			"Paris,Shanghai,Tokyo,Seoul,Cairo").split(",");
		int indx = new Random().nextInt(cities.length);
		return cities[indx];
	}
}