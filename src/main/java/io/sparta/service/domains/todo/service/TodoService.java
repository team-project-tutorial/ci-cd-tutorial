package io.sparta.service.domains.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.sparta.service.domains.todo.controller.dto.CreateTodoRequest;
import io.sparta.service.domains.todo.controller.dto.TodoResponse;
import io.sparta.service.domains.todo.domain.model.Todo;
import io.sparta.service.domains.todo.domain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {
	private final TodoRepository todoRepository;

	@Transactional
	public Long create(CreateTodoRequest createTodoRequest) {
		Todo todo = createTodoRequest.toEntity();
		return todoRepository.save(todo).getId();
	}

	public TodoResponse getTodoById(Long id) {
		return todoRepository.findById(id)
			.map(TodoResponse::from)
			.orElseThrow(() -> new RuntimeException("요청에 해당하는 할일을 찾을 수 없습니다."));
	}

	public PagedModel<TodoResponse> getTodos(Pageable pageable) {
		Page<TodoResponse> pageResponse = todoRepository.findAll(pageable).map(TodoResponse::from);
		return new PagedModel<>(pageResponse);
	}
}
