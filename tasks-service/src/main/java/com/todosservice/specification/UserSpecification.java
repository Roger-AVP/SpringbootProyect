package com.todosservice.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.todosservice.entity.User;
import com.todosservice.entity.User_;


public class UserSpecification {

	public static Specification<User> findByEmail(String email) {
		return new Specification<User>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(User_.email), email);
			}

		};
	}
}
