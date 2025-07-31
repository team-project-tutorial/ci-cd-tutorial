package io.sparta.service.domains.todo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.sparta.service.domains.todo.controller.dto.CreateTodoRequest;
import io.sparta.service.domains.todo.controller.dto.TodoResponse;
import io.sparta.service.domains.todo.domain.model.Todo;
import io.sparta.service.domains.todo.domain.repository.TodoRepository;

@DisplayName("Service:Todo")
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoService todoService;

	@Test
	@DisplayName("할일을 생성한다.")
	void create() {
		// Given
		String title = "title";
		String content = "content";
		CreateTodoRequest createTodoRequest = new CreateTodoRequest(title, content);
		Todo entity = createTodoRequest.toEntity();

		given(todoRepository.save(entity)).willAnswer(AdditionalAnswers.returnsFirstArg());

		// When
		Long createdTodoId = todoService.create(createTodoRequest);

		// Then
		verify(todoRepository, times(1)).save(entity);
	}

	@Test
	@DisplayName("특정 할일을 조회한다.")
	void getTodoById() {
		// Given
		Long id = 1L;
		CreateTodoRequest createTodoRequest = new CreateTodoRequest("title", "content");
		Todo entity = createTodoRequest.toEntity();

		given(todoRepository.findById(id)).willReturn(Optional.of(entity));

		// When
		TodoResponse todoResponse = todoService.getTodoById(id);

		// Then
		assertThat(todoResponse).isNotNull();
		verify(todoRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("특정 할일이 없는 경우 예외가 발생한다.")
	void throwException_whenTodoIsNotExist() {
		// Given
		Long id = 1L;

		// When & Then
		assertThatExceptionOfType(RuntimeException.class)
			.isThrownBy(() -> todoService.getTodoById(id));
	}
}
