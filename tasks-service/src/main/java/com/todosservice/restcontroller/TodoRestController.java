package com.todosservice.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todosservice.entity.Todo;
import com.todosservice.exceptions.TodoException;
import com.todosservice.service.impl.TodoServiceImpl;

@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

	@Autowired
	private TodoServiceImpl todoServiceImpl;

	/* GET METHODS */

	// Obtiene lista de todos los registro de tipo clase 'Todo'
	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public List<Todo> getAll() {
		return todoServiceImpl.getAll();
	}

	// Obtiene un solo registro clase 'Todo', filtrado por el campo todoId
	@RequestMapping(method = RequestMethod.GET, value = "/getById")
	public Todo getById(@RequestParam(name = "todoId", required = true) Long todoId) {
		return todoServiceImpl.getById(todoId);
	}

	// Obtiene una lista de tipo clase 'Todo', filtrados por su campo 'completed'
	@RequestMapping(method = RequestMethod.GET, value = "/getByCompleted")
	public List<Todo> getByCompleted(@RequestParam(name = "completed", required = true) boolean completed) {
		return todoServiceImpl.getByCompleted(completed);
	}

	/* POST METHODS */

	// Registra un nuevo elemento de tipo clase 'Todo'
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public Todo guardar(@RequestBody @Valid Todo todo) throws TodoException {
		return todoServiceImpl.create(todo);
	}

	/* PUT METHODS */

	// Actualiza un registro de tipo clase 'Todo'
	@RequestMapping(method = RequestMethod.PUT, value = "/update")
	public Todo update(@RequestBody @Valid Todo todo) {
		return todoServiceImpl.update(todo);
	}

	// Actualiza varios registros de tipo clase 'Todo', mediante una lista
	@RequestMapping(method = RequestMethod.PUT, value = "/updatePerList")
	public List<Todo> updateByCompleted(@RequestBody @Valid List<Todo> todoList) {
		return todoServiceImpl.updateToggleAll(todoList);
	}

	// Elimina varios registros de tipo clase 'Todo'
	@RequestMapping(method = RequestMethod.PUT, value = "/deletePerList")
	public void deletePerList(@RequestBody List<Todo> todoList) {
		todoServiceImpl.deletePerList(todoList);
	}
	/* DELETE METHODS */

	// Elimina un registro de tipo clase 'Todo'
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public void delete(@RequestParam(name = "todoId", required = true) Long todoId) {
		todoServiceImpl.delete(todoId);
	}

}
