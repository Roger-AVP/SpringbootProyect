package com.todosservice.service;

import java.util.List;

import com.todosservice.entity.Todo;
import com.todosservice.exceptions.TodoException;


public interface TodoService {

	List<Todo> getAll();

	List<Todo> getByCompleted(boolean complete);
	
	List<Todo> updateToggleAll(List<Todo> todoList);

	Todo getById(Long taskId);

	Todo create(Todo task) throws TodoException;

	Todo update(Todo task);

	void delete(Long taskId);

}
