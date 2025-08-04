package io.sparta.service.domains.todo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sparta.service.domains.todo.controller.dto.CreateTodoRequest;
import io.sparta.service.domains.todo.controller.dto.TodoResponse;
import io.sparta.service.domains.todo.controller.dto.UpdateTodoRequest;
import io.sparta.service.domains.todo.service.TodoService;

@ActiveProfiles("test")
@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TodoControllerTest {
	@MockitoBean
	private TodoService todoService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("[POST:201]할일 생성 API")
	@WithMockUser(username = "1")
	void create() throws Exception {
		// Given
		CreateTodoRequest createTodoRequest = new CreateTodoRequest("title", "content");
		when(todoService.create(createTodoRequest)).thenReturn(1L);

		// When
		ResultActions resultActions = mockMvc.perform(post("/api/todos")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(createTodoRequest))
		);

		// Then
		resultActions.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists(LOCATION))
		;
	}

	@Test
	@DisplayName("[GET:200]특정할일 조회 API")
	void getTodoById() throws Exception {
		// Given
		String GET_TODO_URI = "/api/todos/{id}";
		Long id = 1L;
		TodoResponse todoResponse = new TodoResponse(1L, "title", "content", LocalDateTime.now());

		BDDMockito.given(todoService.getTodoById(id)).willReturn(todoResponse);

		// When
		ResultActions resultActions = mockMvc.perform(get(GET_TODO_URI, id));

		// Then
		resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
		;
	}

	@Test
	@DisplayName("[GET:200]할일 목록 조회 API")
	void getTodo() throws Exception {
		// Given
		int size = 10;
		PageRequest pageRequest = PageRequest.of(0, size);
		BDDMockito.given(todoService.getTodos(pageRequest))
			.willReturn(createMockPagedModel());

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/todos")
			.param("page", Integer.toString(1))
			.param("size", Integer.toString(10))
		);

		// Then
		resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.length()").value(5))
			.andExpect(jsonPath("$.content[0].title").value("제목 1"));
		;
	}

	@Test
	@DisplayName("[GET:400]할일 목록 조회 API")
	void throwException_overThanMaxPageSizeRequest() throws Exception {
		// Given
		int size = 1002;
		PageRequest pageRequest = PageRequest.of(0, size);
		BDDMockito.given(todoService.getTodos(pageRequest))
			.willReturn(createMockPagedModel());

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/todos")
			.param("page", Integer.toString(1))
			.param("size", Integer.toString(size))
		);

		// Then
		resultActions.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("[PUT:200] 할일 수정")
	void update() throws Exception {
		// Given
		String PUT_TODO_URI = "/api/todos/{id}";
		Long id = 1L;
		UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest(
			"new title",
			"new content"
		);
		TodoResponse todoResponse = new TodoResponse(1L, "new title", "new content", LocalDateTime.now());

		BDDMockito.given(todoService.update(id, updateTodoRequest)).willReturn(todoResponse);

		// When
		ResultActions resultActions = mockMvc.perform(put(PUT_TODO_URI, id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateTodoRequest))
		);

		// Then
		resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
		;
	}

	private List<TodoResponse> createTodoResponses(int count) {
		List<TodoResponse> list = new ArrayList<>();
		for (long i = 1; i <= count; i++) {
			list.add(new TodoResponse(
				i,
				"제목 " + i,
				"내용 " + i,
				LocalDateTime.now().minusDays(i)
			));
		}
		return list;
	}

	private PagedModel<TodoResponse> createMockPagedModel() {
		int page = 0;
		int size = 10;
		List<TodoResponse> content = createTodoResponses(5);
		Page<TodoResponse> dtoPage = new PageImpl<>(content, PageRequest.of(page, size), 5);
		return new PagedModel<>(dtoPage);
	}
}
