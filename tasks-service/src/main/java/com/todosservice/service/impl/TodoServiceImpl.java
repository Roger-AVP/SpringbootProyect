package com.todosservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todosservice.entity.Todo;
import com.todosservice.exceptions.TodoException;
import com.todosservice.repository.TodoRepository;
import com.todosservice.service.TodoService;
import com.todosservice.specification.TodoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;

	@Override
	public List<Todo> getAll() {
		return todoRepository.findAll();
	}

	@Override
	public Todo getById(Long todoId) {
		return todoRepository.findById(todoId).orElse(null);
	}

	@Override
	public Todo create(Todo todo) throws TodoException {
		return todoRepository.save(todo);
	}

	@Override
	public Todo update(Todo todo) {
		return todoRepository.save(todo);
	}

	@Override
	public void delete(Long todoId) {
		todoRepository.deleteById(todoId);
	}

	@Override
	public List<Todo> getByCompleted(boolean complete) {
		return todoRepository.findAll(TodoSpecification.findByCompleted(complete));

	}

	@Override
	public List<Todo> updateToggleAll(List<Todo> todoList) {
		return todoRepository.saveAll(todoList);
	}

	@Override
	public void deletePerList(List<Todo> todos) {
		todoRepository.deleteAll(todos);
	}

}
