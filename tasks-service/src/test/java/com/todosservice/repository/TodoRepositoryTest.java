package com.todosservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.todosservice.entity.Todo;

@SpringBootTest
@Transactional
public class TodoRepositoryTest {

	@Autowired
	private TodoRepository todoRepository;

	@Test
	public void testSaveTodo() {
		Todo todo = new Todo(null, "Nueva tarea", false);
		Todo savedTodo = todoRepository.save(todo);

		assertNotNull(savedTodo);
	}

	@Test
	public void testSaveAllTodos() {
		Todo todo2 = new Todo(null, "Tarea 2", false);
		Todo todo3 = new Todo(null, "Tarea 3", false);
		Todo todo4 = new Todo(null, "Tarea 4", false);

		List<Todo> todos = new ArrayList<Todo>();
		todos.add(todo2);
		todos.add(todo3);
		todos.add(todo4);

		List<Todo> savedTodos = todoRepository.saveAll(todos);

		assertEquals(3, savedTodos.size());
	}

	@Test
	public void testUpdateTodo() {
		Todo todo = new Todo(null, "Tarea de prueba", false);
		Todo savedTodo = todoRepository.save(todo);

		// Cambia de nombre al registro String newTitle = "Tarea nueva";
		String newTitle = "Tarea nueva";
		savedTodo.setTitle(newTitle);
		
		savedTodo = todoRepository.save(savedTodo);

		assertEquals(newTitle, savedTodo.getTitle());
	}

	@Test
	public void testGetListTodos() {
		List<Todo> todos = todoRepository.findAll();

		assertThat(todos).size().isGreaterThan(0);
	}

	@Test
	public void testDeleteTodo() {
		Todo todo = new Todo(null, "deleteTodo", false);
		Todo savedTodo = todoRepository.save(todo);

		boolean isExistTodo = todoRepository.findById(savedTodo.getTodoId()).isPresent();

		todoRepository.deleteById(savedTodo.getTodoId());

		boolean notExistTodo = todoRepository.findById(savedTodo.getTodoId()).isPresent();

		assertTrue(isExistTodo);
		assertFalse(notExistTodo);
	}
	

	@Test
	public void testDeletePerListTodo() {
		// Crea objetos de tipo 'Todo'
		Todo todo2 = new Todo(10L, "Tarea 2.1", false);
		Todo todo3 = new Todo(11L, "Tarea 3.1", false);
		Todo todo4 = new Todo(12L, "Tarea 4.1", false);

		// Crea una lista de objetos tipo 'Todo'
		List<Todo> todos = new ArrayList<Todo>();
		todos.add(todo2);
		todos.add(todo3);
		todos.add(todo4);
		
		// Registra una lista de registro tipo 'Todo'
		List<Todo> savedTodos = todoRepository.saveAll(todos);

		// Evalúa si se registraron la cantidad de objetos ingresado en la lista
		assertEquals(3, savedTodos.size());
		
		// Elimina los datos que fueron registrados de tipo 'Todo'
		todoRepository.deleteAll(savedTodos);
		
		// Crea una lista de ids tipo Long
		List<Long> ids = new ArrayList<Long>();
		ids.add(10L);
		ids.add(11L);
		ids.add(12L);
		
		// Retorna una lista de registros consultando mediante la lista de ids
		List<Todo> todosFetched = todoRepository.findAllById(ids);

		// Evalúa si se eliminaron los registros
		assertEquals(0, todosFetched.size());
	}

}
