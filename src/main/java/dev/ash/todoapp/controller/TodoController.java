package dev.ash.todoapp.controller;

import dev.ash.todoapp.model.Todo;
import dev.ash.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new Todo") //documentation
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(201).body(todoService.createTodo(todo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets todo data by id") // documentation
    @ApiResponses({  // documentation
            @ApiResponse(responseCode = "200", description = "Todo Found"),
            @ApiResponse(responseCode = "404", description = "Todo Not Found")
    })
    public ResponseEntity<Todo> getTodoById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(todoService.getTodoById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getAllTodos(){
        List<Todo> todos = todoService.getAllTodos();
        if(todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/paged")   //ex: GET /todos?page=1&size=10&sortBy=title
    @Operation(summary = "Gets todo data by page")   // documentation
    @ApiResponses({   // documentation
            @ApiResponse(responseCode = "200", description = "Todo Found"),
            @ApiResponse(responseCode = "404", description = "Todo Not Found")
    })
    public ResponseEntity<Page<Todo>> getTodoByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy ) {
        Page<Todo> pagedTodos = todoService.getTodoByPage(page, size, sortBy);
        if(pagedTodos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagedTodos);
    }

    @PutMapping("/update")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        try {
            return ResponseEntity.ok(todoService.updateTodo(todo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Todo> deleteById(@PathVariable long id) {
        try {
            todoService.deleteTodoById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Todo> deleteAll() {
        todoService.deleteAllTodos();
        return ResponseEntity.noContent().build();
    }

}
