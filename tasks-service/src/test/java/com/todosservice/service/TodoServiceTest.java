package com.todosservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.todosservice.entity.Todo;
import com.todosservice.exceptions.TodoException;
import com.todosservice.repository.TodoRepository;
import com.todosservice.service.TodoService;
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

		// Creo un nuevo objeto de la Entidad Todo
		Todo todo = new Todo(1L, "primera tarea", false);

		// Agrego datos falsos al metodo save del Repository
		Mockito.when(todoRepository.save(todo)).thenReturn(todo);

	}

	@Test
	public void testCreateTodoService() throws TodoException {

		Todo todo = new Todo(1L, "primera tarea", false);

		Todo savedTodo = todoService.create(todo);

		Assertions.assertThat(savedTodo.getTodoId().equals(todo.getTodoId()));
	}

	@Test
	public void testUpdateTodoService() throws TodoException {

		Todo todo = new Todo(1L, "primera tarea", false);

		Todo savedTodo = todoService.create(todo);
		savedTodo.setCompleted(true);

		Todo updatedTodo = todoService.update(savedTodo);

		assertFalse(todo.isCompleted());
		assertTrue(updatedTodo.isCompleted());
	}

}
