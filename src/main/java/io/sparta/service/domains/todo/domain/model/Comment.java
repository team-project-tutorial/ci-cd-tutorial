package io.sparta.service.domains.todo.domain.model;

import io.sparta.service.common.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private String contents;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "todo_id", nullable = false)
	private Todo todo;

	private Comment(Long userId, String contents, Todo todo) {
		this.userId = userId;
		this.contents = contents;
		this.todo = todo;
	}

	public static Comment of(Long userId, String contents, Todo todo) {
		return new Comment(userId, contents, todo);
	}

	public void update(String contents) {
		this.contents = contents;
	}
}
