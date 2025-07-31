package io.sparta.service.domains.todo.controller.dto;

import java.time.LocalDateTime;

import io.sparta.service.domains.todo.domain.model.Todo;

public record TodoResponse(
	Long id,
	String title,
	String content,
	LocalDateTime createdAt
) {
	public static TodoResponse from(Todo todo) {
		return new TodoResponse(
			todo.getId(),
			todo.getTitle(),
			todo.getContents(),
			todo.getCreatedAt()
		);
	}
}
