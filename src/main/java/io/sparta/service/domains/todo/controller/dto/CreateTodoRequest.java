package io.sparta.service.domains.todo.controller.dto;

import io.sparta.service.domains.todo.domain.model.Todo;

public record CreateTodoRequest(
	String title,
	String content
) {
	public Todo toEntity() {
		return Todo.of(title, content);
	}

}
