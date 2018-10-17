package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Region("temps")
public class Temp {

	@Id
	private Long id;
	private double temp;
	private String city;
}
