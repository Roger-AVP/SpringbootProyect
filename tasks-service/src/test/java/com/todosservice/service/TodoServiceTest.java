package com.todosservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todosservice.entity.Todo;
import com.todosservice.exceptions.TodoException;
import com.todosservice.repository.TodoRepository;
import com.todosservice.service.impl.TodoServiceImpl;

@SpringBootTest
public class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	// Configura Mockito para que realice las operaciones necesarias de
	// TodoRepository
	@BeforeEach
	public void setup() {

		// creo la instancia del Servicio inyectando la dependecia 'todoRepository' de
		// Mock
		todoService = new TodoServiceImpl(todoRepository);

		// Crea objetos de tipo clase 'Todo'
		Todo todo = new Todo(1L, "primera tarea", false);
		Todo todo2 = new Todo(1L, "segunda tarea", false);
		Todo todo3 = new Todo(1L, "tercera tarea", false);

		// Crea una lista de clases 'Todo'
		List<Todo> todos = new ArrayList<Todo>();
		todos.add(todo);
		todos.add(todo2);
		todos.add(todo3);

		// Retorna un registro de la Entidad Todo
		Mockito.when(todoRepository.save(todo)).thenReturn(todo);

		// Retorna una lista de objetos 'Todo'
		Mockito.when(todoRepository.findAll()).thenReturn(todos);

	}

	@Test
	public void testGetAllTodoService() throws TodoException {
		List<Todo> todos = todoService.getAll();

		assertEquals(3, todos.size());
	}

	@Test
	public void testCreateTodoService() throws TodoException {
		// Crea un objeto tipo 'Todo'
		Todo todo = new Todo(1L, "primera tarea", false);

		// Inserta el registro
		Todo savedTodo = todoService.create(todo);

		Assertions.assertThat(savedTodo.getTodoId().equals(todo.getTodoId()));
	}

	@Test
	public void testUpdateTodoService() throws TodoException {
		// Crea un objeto tipo 'Todo'
		Todo todo = new Todo(1L, "primera tarea", false);

		// Inserta el registro
		Todo savedTodo = todoService.create(todo);

		// Edita en memoria registro, modificando el valor del campo 'completed'
		savedTodo.setCompleted(true);

		// Actualiza el registro
		Todo updatedTodo = todoService.update(savedTodo);

		// Evalua el valor del campo 'completed' luego de crear el registro
		assertFalse(todo.isCompleted());

		// Evalua el valor del campo 'completed' luego de actualizar el registro
		assertTrue(updatedTodo.isCompleted());
	}
}
