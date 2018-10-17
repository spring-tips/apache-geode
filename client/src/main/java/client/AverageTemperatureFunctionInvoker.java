package client;

import org.springframework.data.gemfire.function.annotation.OnRegion;

import java.util.Collection;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@OnRegion(region = "temps")
public interface AverageTemperatureFunctionInvoker {

	Collection<Double> average();
}
