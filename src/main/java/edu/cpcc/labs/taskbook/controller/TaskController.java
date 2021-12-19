package edu.cpcc.labs.taskbook.controller;

import java.lang.System.Logger;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cpcc.labs.taskbook.model.Task;
import edu.cpcc.labs.taskbook.model.TaskStatus;
import edu.cpcc.labs.taskbook.service.TaskService;

@RestController
@RequestMapping ("/api/taskbook/tasks")
public class TaskController {
		
	TaskService taskService; 
		
	Logger logger = LoggerFactory.getLogger(TaskController.class.getName());	
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;		
	}
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@GetMapping()
	public List<Task> getAllTasks() {
		return this.taskService.get();
	}

	// URI: /api/taskbook/tasks?status=<value>
	// URL: http://localhost:8080/api/taskbook/tasks/specifictasks?status=<value>
	// NOTE: doing this since I need to distinguish this URI from the previous URI
	@GetMapping("/specifictasks")
	public List<Task> getAllTasks(@RequestParam(name="status") TaskStatus value) {
		return this.taskService.get(value);
	}
	
	// URI: /api/taskbook/tasks/<id-value>
	// URL: http://localhost:8080/api/taskbook/tasks/<id-value>
	@GetMapping("/{id}")
	public Optional<Task> get(@PathVariable @NotNull Integer id) {
		return this.taskService.get(id);
	}
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@PostMapping()
	public Task create(@RequestBody @NotNull @Valid Task task) {
		return this.taskService.create(task);		
	}
	
	
	// URI: /api/taskbook/tasks
	// URL: http://localhost:8080/api/taskbook/tasks	
	@PutMapping()
	public ResponseEntity<Task> update(@RequestBody @NotNull @Valid Task task) {
		Task updatedTask =  this.taskService.update(task);		
		if (updatedTask == null) {
			String responseText = "Task NOT found with that id: [" + task.getId() + "]";
			return ResponseEntity.notFound().header("Task", responseText).build();
		}
		return ResponseEntity.ok(updatedTask);
	}
	
	// URI: /api/taskbook/tasks?status=<value>
	// URL: http://localhost:8080/api/taskbook/<value of the task id>
	@DeleteMapping("/{id}")
	public ResponseEntity<Task> delete(@PathVariable @NotNull Integer id) {
		Task foundTask = this.taskService.delete(id);
		if (foundTask == null) {
			String responseText = "Task NOT found with that id: [" + id + "]";
			return ResponseEntity.notFound().header("Task", responseText).build();
		}
		return ResponseEntity.ok().build();		
	}
	

}
