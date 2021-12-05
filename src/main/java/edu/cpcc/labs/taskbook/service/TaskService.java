package edu.cpcc.labs.taskbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cpcc.labs.taskbook.dao.TaskRepository;
import edu.cpcc.labs.taskbook.model.Task;
import edu.cpcc.labs.taskbook.model.TaskStatus;

@Service
public class TaskService {
	
	TaskRepository taskRespository;
	
	@Autowired
	public TaskService(TaskRepository taskRespository) {
		this.taskRespository = taskRespository;		
	}
	
	public List<Task> get() {
		return this.taskRespository.findAll();
	}

	public List<Task> get(final TaskStatus value) {
		return this.taskRespository.findTasksByStatus(value.name());
	}
	
	public Task get(final Integer id) {
		return this.taskRespository.getById(id);
	}
	
	public Task create(final Task task) {
		return this.taskRespository.save(task);		
	}
	
	public Task update(final Task task) {
		return this.taskRespository.save(task);		
	}
	
	public void delete(final Integer id) {
		this.taskRespository.deleteById(id);	
	}

}
