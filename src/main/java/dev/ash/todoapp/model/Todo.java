package dev.ash.todoapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity   // Marks as an entity for DI and hibernate
@Data  // Generates getters and setters using lombok
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    @Schema(description = "Unique Id for todo entry", example = "101") // documentation
    private long id;
    @NotBlank
    @Size(min = 4, message = "title too short")
    @Schema(description = "todo title", example = "Finish Todo Application frontend") // documentation
    private String title;
    private String description;
    private boolean isCompleted;
}
