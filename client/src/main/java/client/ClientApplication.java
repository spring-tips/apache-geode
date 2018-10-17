package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Random;

@SpringBootApplication
@EnableClusterConfiguration // will create 'temps' for me
@EnableEntityDefinedRegions
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Region("temps")
class Temp {

	@Id
	private Long id;
	private double temp;
	private String city;
}

interface TempRepository extends CrudRepository<Temp, Long> {
}

@Component
class Runner implements ApplicationListener<ApplicationReadyEvent> {

	private final TempRepository tempRepository;

	Runner(TempRepository tempRepository) {
		this.tempRepository = tempRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.tempRepository
			.findAll()
			.forEach(this.tempRepository::delete);
		int start = (int) (Math.random() * 100);
		for (int i = start; i < start + 100; i++) {
			this.insertRecord();
		}
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
