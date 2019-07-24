package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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
	@Column(table = "field_mobile")
	private List<Field> fieldsMobile = new ArrayList<>();

	public void updateFieldsDesktop(ArrayList<Field> newFields)
	{
		updateFields(this.fieldsDesktop, newFields);
	}

	public void updateFieldsMobile(ArrayList<Field> newFields)
	{
		updateFields(this.fieldsMobile, newFields);
	}

	private void updateFields(List<Field> fields, ArrayList<Field> newFields)
	{
		ArrayList<String> fieldnames = (ArrayList<String>) fields.stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newFields)
		{
			if (!fieldnames.contains(newField.getName()))
				fields.add(newField);
			else
			{
				int i = fieldnames.indexOf(newField.getName());
				newField.setId(fields.get(i).getId());
				fields.set(i, newField);
			}
		}
	}
}
