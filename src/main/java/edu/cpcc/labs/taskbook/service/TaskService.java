package edu.cpcc.labs.taskbook.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cpcc.labs.taskbook.dao.TaskRepository;
import edu.cpcc.labs.taskbook.exception.ApiResourceNotFoundException;
import edu.cpcc.labs.taskbook.model.Task;
import edu.cpcc.labs.taskbook.model.TaskStatus;

@Service
public class TaskService {
	
	TaskRepository taskRespository;
	
	Logger logger = LoggerFactory.getLogger(TaskService.class.getName());
	
	@Autowired
	public TaskService(TaskRepository taskRespository) {
		this.taskRespository = taskRespository;		
	}
	
	// NOTE: for every endpoint in the TaskController there is one corresponding method in this service..
	
	public List<Task> get() {
		return this.taskRespository.findAll();
	}

	public List<Task> get(final TaskStatus value) {
		return this.taskRespository.findAllTasksByStatus(value.name());
	}
	
	public Optional<Task> get(final Integer id) {
		// NOTE: taksRepository.getById does not work as expected
		return this.taskRespository.findById(id);
	}
	
	public Task create(final Task task) {
		return this.taskRespository.save(task);		
	}
	
	public Task update(final Task taskToUpdate) {
		// this is a just to see if the task exists in the table... to do the update....
		this.taskRespository.findById(taskToUpdate.getId()).orElseThrow(()->new ApiResourceNotFoundException("Task not found for id: " + taskToUpdate.getId()));
		Task updatedTask = this.taskRespository.save(taskToUpdate);		
		return updatedTask;
	}
	
	public Task delete(final Integer id) {
		Task foundTaskToDelete = this.taskRespository.findById(id).orElseThrow(()->new ApiResourceNotFoundException("Task not found for id: " + id));
		this.taskRespository.delete(foundTaskToDelete);
		return foundTaskToDelete; 
	}

}
