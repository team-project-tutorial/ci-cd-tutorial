package io.sparta.service.domains.todo.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.sparta.service.common.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todos")
public class Todo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String contents;

	@OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	private Todo(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public static Todo of(String title, String contents) {
		return new Todo(title, contents);
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Todo todo = (Todo)o;
		return Objects.equals(id, todo.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
