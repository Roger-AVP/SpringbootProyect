package com.todosservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "name", nullable = false, length = 20, unique = true)
	@Size(min = 2, max = 20)
	private String name;

	@Column(name = "email", nullable = false, length = 100, unique = true)
	@Size(max = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	@Size(min = 2, max = 100)
	private String password = "password";

	@Transient
	private String token;

}
