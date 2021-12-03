package edu.cpcc.labs.taskbook.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import edu.cpcc.labs.taskbook.model.Task;

@RestController
@RequestMapping ("/api/taskbook/tasks")
public class TaskController {
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@GetMapping()
	public List<Task> getAllTasks() {
		return null;
	}

	// URI: /api/taskbook/tasks?status=<value>
	// URL: http://localhost:8080/api/taskbook/tasks?status=<value>
	@GetMapping()
	public List<Task> getAllTasks(@RequestParam(name="status") String value) {
		return null;
	}
	
	// URI: /api/taskbook/tasks/<id-value>
	// URL: http://localhost:8080/api/taskbook/tasks/<id-value>
	@GetMapping("/{id}")
	public Task get(@PathVariable @NotNull Integer id) {
		return null;
	}
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@PostMapping()
	public Task create(@RequestBody @Valid Task task) {
		return null;		
	}
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@PutMapping()
	public Task update(@RequestBody @Valid Task task) {
		return null;		
	}
	
	// URI: /api/taskbook/tasks?status=<value>
	// URL: http://localhost:8080/api/taskbook/tasks?status=<value>
	@DeleteMapping()
	public Task delete(@PathVariable @NotNull Integer id) {
		return null;		
	}
	

}
