package org.bioauth.typeauth.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Component
@Entity
@Data
@NoArgsConstructor
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;

	@NotNull
	private Double totalElapsedTime;

	@NotNull
	private Double totalPressTime;

	public HashMap<String, Object> getScore(Field field)
	{
		HashMap<String, Object> score = new HashMap<>();
		score.put("name", name);
		Double score_tet =  100 * Math.min(totalElapsedTime, field.getTotalElapsedTime())
			/ Math.max(totalElapsedTime, field.getTotalElapsedTime());
		Double score_tpt =  100 * Math.min(totalPressTime, field.totalPressTime)
			/ Math.max(totalPressTime, field.totalPressTime);
		score.put("score_tet", score_tet.intValue());
		score.put("score_tpt", score_tpt.intValue());
		return score;
	}
}
