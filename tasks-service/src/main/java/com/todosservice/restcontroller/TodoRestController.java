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

	// GET METHODS

	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public List<Todo> getAll() {
		return todoServiceImpl.getAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getById")
	public Todo getById(@RequestParam(name = "todoId", required = true) Long todoId) {
		return todoServiceImpl.getById(todoId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getByCompleted")
	public List<Todo> getByCompleted(@RequestParam(name = "completed", required = true) boolean completed) {
		return todoServiceImpl.getByCompleted(completed);
	}

	// POST METHODS
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public Todo guardar(@RequestBody @Valid Todo todo) throws TodoException{
		return todoServiceImpl.create(todo);
	}

	// DELETE METHODS
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public void delete(@RequestParam(name = "todoId", required = true) Long todoId) {
		todoServiceImpl.delete(todoId);
	}

	// PUT METHODS
	@RequestMapping(method = RequestMethod.PUT, value = "/update")
	public Todo update(@RequestBody @Valid Todo todo) {
		return todoServiceImpl.update(todo);
	}


	@RequestMapping(method = RequestMethod.PUT, value = "/updateToggleAll")
	public List<Todo> updateByCompleted(@RequestBody @Valid List<Todo> todoList) {
		return todoServiceImpl.updateToggleAll(todoList);
	}

}
