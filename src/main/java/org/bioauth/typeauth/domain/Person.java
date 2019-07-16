package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

	public void updateFields(ArrayList<Field> newFields)
	{
		ArrayList<String> fieldnames = (ArrayList<String>) fields.stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newFields)
		{
			if (!fieldnames.contains(newField.getName()))
				fields.add(newField);
			else
			{
				System.out.println(newField.getName());
				int i = fieldnames.indexOf(newField.getName());
				newField.setId(fields.get(i).getId());
				fields.set(i, newField);
			}
		}
	}
}
