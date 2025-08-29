package dev.ash.todoapp.service;

import dev.ash.todoapp.customexceptions.ResourceNotFoundException;
import dev.ash.todoapp.model.AppUser;
import dev.ash.todoapp.model.Todo;
import dev.ash.todoapp.repository.AppUserRepository;
import dev.ash.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AppUserRepository userRepository;

    private AppUser getCurrUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Todo createTodo(Todo todo) {
        todo.setUser(getCurrUser());
        return todoRepository.save(todo);
    }

    public Todo getUserTodoById(long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));

        if (!todo.getUser().equals(getCurrUser())) {
            throw new ResourceNotFoundException("Todo not found for this user");
        }
        return todo;
    }

    public List<Todo> getAllUserTodos() {
        return todoRepository.findByUser(getCurrUser());
    }

    public Page<Todo> getTodoByPage(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return todoRepository.findByUser(getCurrUser(), pageable);
    }

    public Todo updateTodo(long id, Todo updatedTodo) {
        Todo existingTodo = getUserTodoById(id);

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        return todoRepository.save(existingTodo);
    }

    public void deleteUserTodoById(long id) {
        Todo todo = getUserTodoById(id);
        todoRepository.delete(todo);
    }

    public void deleteAllUserTodos() {
        todoRepository.deleteAll(todoRepository.findByUser(getCurrUser()));
    }
}
