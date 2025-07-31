package io.sparta.service.domains.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.sparta.service.domains.todo.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
