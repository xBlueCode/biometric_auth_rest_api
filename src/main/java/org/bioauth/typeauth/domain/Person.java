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
	@Column(table = "field_desktop")
	private List<Field> fieldsDesktop = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			joinColumns = @JoinColumn(name = "PERSON_ID"),
			inverseJoinColumns = @JoinColumn(name = "FIELD_ID")
	)
	@Column(table = "field_phone")
	private List<Field> fieldsPhone = new ArrayList<>();

	public void updateFieldsDesktop(ArrayList<Field> newFields)
	{
		ArrayList<String> fieldnames = (ArrayList<String>) fieldsDesktop.stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newFields)
		{
			if (!fieldnames.contains(newField.getName()))
				fieldsDesktop.add(newField);
			else
			{
				System.out.println(newField.getName());
				int i = fieldnames.indexOf(newField.getName());
				newField.setId(fieldsDesktop.get(i).getId());
				fieldsDesktop.set(i, newField);
			}
		}
	}
	public void updateFieldsPhone(ArrayList<Field> newFields)
	{
		ArrayList<String> fieldnames = (ArrayList<String>) fieldsPhone.stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newFields)
		{
			if (!fieldnames.contains(newField.getName()))
				fieldsPhone.add(newField);
			else
			{
				System.out.println(newField.getName());
				int i = fieldnames.indexOf(newField.getName());
				newField.setId(fieldsPhone.get(i).getId());
				fieldsPhone.set(i, newField);
			}
		}
	}
}
