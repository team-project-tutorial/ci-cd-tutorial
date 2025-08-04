package io.sparta.service.domains.todo.controller.dto;

public record UpdateTodoRequest(
	String title,
	String content
) {
}
