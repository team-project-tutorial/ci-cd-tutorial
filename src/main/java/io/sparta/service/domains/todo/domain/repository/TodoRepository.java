package io.sparta.service.domains.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.sparta.service.domains.todo.domain.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
