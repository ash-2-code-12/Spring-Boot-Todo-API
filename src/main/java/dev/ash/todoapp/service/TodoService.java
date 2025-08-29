package dev.ash.todoapp.service;

import dev.ash.todoapp.customexceptions.ResourceNotFoundException;
import dev.ash.todoapp.model.Todo;
import dev.ash.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo getTodoById(long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Page<Todo> getTodoByPage(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return todoRepository.findAll(pageable);
    }

    public Todo updateTodo(Todo updatedTodo) {
        Todo existingTodo = getTodoById(updatedTodo.getId());

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        return todoRepository.save(existingTodo);
    }

    public void deleteTodoById(long id) {
        getTodoById(id);
        todoRepository.deleteById(id);
    }

    public void deleteTodo(Todo todo) {
        todoRepository.deleteById(todo.getId());
    }

    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }
}
