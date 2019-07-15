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
}
