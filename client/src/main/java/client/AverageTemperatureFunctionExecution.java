package client;

import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region = "temps")
public interface AverageTemperatureFunctionExecution {

	Object averageTemperature();

}
