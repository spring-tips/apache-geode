package server.fn;

import common.Temp;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.data.gemfire.function.annotation.RegionData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TempAverageFunction {

	@GemfireFunction
	public Double averageTemperature(@RegionData Map<String, Temp> temps) {

		return temps.values()
			.stream()
			.collect(Collectors.averagingDouble(Temp::getTemp));

	}
}
