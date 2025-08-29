package dev.ash.todoapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition( //documentation
        info = @Info(title = "Todo API", version = "1.0", description = "Manage Todos"),
        tags = {@Tag(name = "Todo API", description = "Operations related to Todos")}
)
@SpringBootApplication
public class TodoappApplication {

	public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
	}

}
