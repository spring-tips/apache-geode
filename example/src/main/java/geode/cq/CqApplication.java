package geode.cq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.geode.cache.query.CqEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.stereotype.Service;

import java.io.IOException;

@EnableClusterConfiguration
@SpringBootApplication
@Log4j2
public class CqApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(geode.client.ClientApplication.class, args);
		System.in.read();
	}
}

@Log4j2
@Service
class CqService {

	@ContinuousQuery(name = "temps-cq", query = "select * from /temps")
	public void onNewTemp(CqEvent event) {
		Temp temp = Temp.class.cast(event.getNewValue());
		log.info("new result: " + temp.toString());
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
}
