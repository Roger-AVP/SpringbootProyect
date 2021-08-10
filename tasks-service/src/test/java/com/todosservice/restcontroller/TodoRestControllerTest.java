package com.todosservice.restcontroller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.*;
// import static org.hamcrest.Matchers.*;

import com.todosservice.entity.Todo;
import com.todosservice.service.impl.TodoServiceImpl;

/*
@SpringBootTest */
@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoRestController.class)
public class TodoRestControllerTest {

	/* @MockBean
	TodoServiceImpl todoServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAllTodos() throws Exception {
		// Crea objetos de tipo clase 'Todo'
		Todo todo = new Todo(1L, "todo 1", true);
		Todo todo2 = new Todo(2L, "todo 2", false);

		Mockito.when(todoServiceImpl.getAll()).thenReturn(Arrays.asList(todo, todo2));

		mockMvc.perform(get("/api/todos/getAll")).andExpect(status().isOk()).andReturn();*/

		/*
		 * .andExpect(jsonPath("$.length", is(2))) .andExpect(jsonPath("$[0].todoId",
		 * is(1))).andExpect(jsonPath("$[0].title", is("todo 1")))
		 * .andExpect(jsonPath("$[0].completed",
		 * is(true))).andExpect(jsonPath("$[0].todoId", is(2)))
		 * .andExpect(jsonPath("$[0].title",
		 * is("todo 2"))).andExpect(jsonPath("$[0].completed", is(false)));
		 */

	//}

}
