package server.fn;

import common.Temperature;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.data.gemfire.function.annotation.RegionData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Component
@Log4j2
public class AverageTemperatureFunctionImpl {

	@GemfireFunction
	public Double average(@RegionData Map<String, Temperature> data) {
		Double avg = data
			.values()
			.stream()
			.map(Temperature::getTemperature)
			.collect(Collectors.averagingDouble(x -> x));

		log.info("arrived at average: " + avg);
		return avg;
	}
}
