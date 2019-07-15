package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			joinColumns = @JoinColumn(name = "PERSON_ID"),
			inverseJoinColumns = @JoinColumn(name = "FIELD_ID")
	)
	private List<Field> fields = new ArrayList<>();

	/*
	@NotNull
	private Double totalPressTime;

	@NotNull
	private Double totalElapsedTime;
*/
	/*
	public Person copy()
	{
		Person person = new Person();
		person.setId(id);
		person.setName(name);
		person.setTotalElapsedTime(totalElapsedTime);
		person.setTotalPressTime(totalPressTime);
		return person;
	}
	 */
}
