package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@Entity
@Data
@NoArgsConstructor
public class Person{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String name;

	@NotNull
	private Double totalPressTime;

	@NotNull
	private Double totalElapsedTime;

	public Person copy()
	{
		Person person = new Person();
		person.setId(id);
		person.setName(name);
		person.setTotalElapsedTime(totalElapsedTime);
		person.setTotalPressTime(totalPressTime);
		return person;
	}
}
