package com.todosservice.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.todosservice.entity.Todo;
import com.todosservice.entity.Todo_;

public class TodoSpecification {

	public static Specification<Todo> findByCompleted(boolean completed) {
		return new Specification<Todo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(Todo_.completed), completed);
			}

		};

	}

}