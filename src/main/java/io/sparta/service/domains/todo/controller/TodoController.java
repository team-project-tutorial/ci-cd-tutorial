package io.sparta.service.domains.todo.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.sparta.service.domains.todo.controller.dto.CreateTodoRequest;
import io.sparta.service.domains.todo.controller.dto.TodoResponse;
import io.sparta.service.domains.todo.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
	private final TodoService todoService;

	@PostMapping
	ResponseEntity<Void> create(
		@RequestBody CreateTodoRequest createTodoRequest
	) {
		Long createdId = todoService.create(createTodoRequest);
		URI uri = UriComponentsBuilder.fromPath("/api/todos/{id}").build(createdId);
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("{id}")
	ResponseEntity<TodoResponse> getTodo(@PathVariable("id") Long id) {
		TodoResponse todoResponse = todoService.getTodoById(id);
		return ResponseEntity.ok(todoResponse);
	}

	@GetMapping
	ResponseEntity<PagedModel<TodoResponse>> getTodos(@PageableDefault Pageable pageable) {
		PagedModel<TodoResponse> pageResponse = todoService.getTodos(pageable);
		return ResponseEntity.ok(pageResponse);
	}
}
