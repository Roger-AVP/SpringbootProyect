package com.todosservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

	@Id
	@Column(name = "todo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long todoId;

	@NotEmpty(message = "El campo no debe ser vacío")
	@Size(min = 1, max = 20)
	@Column(name = "title", nullable = false, length = 20)
	private String title;

	@Column(name = "completed", nullable = true)
	private boolean completed;

}
