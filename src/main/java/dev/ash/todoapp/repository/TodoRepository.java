package dev.ash.todoapp.repository;

import dev.ash.todoapp.model.AppUser;
import dev.ash.todoapp.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(AppUser user);
    Page<Todo> findByUser(AppUser user, Pageable pageable);
}
