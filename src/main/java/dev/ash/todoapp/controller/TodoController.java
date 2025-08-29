package dev.ash.todoapp.controller;

import dev.ash.todoapp.model.Todo;
import dev.ash.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "Create a new Todo")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo Found"),
            @ApiResponse(responseCode = "404", description = "Todo Not Found")
    })
    public ResponseEntity<Todo> getTodoById(@PathVariable long id) {
        return ResponseEntity.ok(todoService.getUserTodoById(id));
    }

    @GetMapping
    @Operation(summary = "Get all todos for the current user")
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllUserTodos();
        return todos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(todos);
    }

    @GetMapping("/paged")
    @Operation(summary = "Get todos by page")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todos Found"),
            @ApiResponse(responseCode = "204", description = "No Todos Found")
    })
    public ResponseEntity<Page<Todo>> getTodoByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<Todo> pagedTodos = todoService.getTodoByPage(page, size, sortBy);
        return pagedTodos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pagedTodos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a todo by id")
    public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.updateTodo(id, todo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo by id")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        todoService.deleteUserTodoById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete all todos of the current user")
    public ResponseEntity<Void> deleteAll() {
        todoService.deleteAllUserTodos();
        return ResponseEntity.noContent().build();
    }
}
