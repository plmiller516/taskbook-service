package edu.cpcc.labs.taskbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Java validation libraray...
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty (message="Task Name cannot be null or empty.")
	@Column(nullable = false)
	private String name;
	
	@NotEmpty (message="Task Description cannot be null or empty.")
	@Column(nullable = false)
	private String description;
	
	// NOTE: check this article to see how to model enumerated constants...
	// https://www.baeldung.com/jpa-persisting-enums-in-jpa --- nice article...
	@Enumerated(EnumType.STRING)
	// NOTE: if you use @Enumerated(EnumType.ORDINAL) the enumerated constant value will be stored as an integer
	@Column(nullable = false)
	private TaskStatus status;
	
	@NotEmpty (message="Created By cannot be null.")
	@Column(nullable = false)
	private String createdBy;
	
	public Task() {		
	}	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	public static void main(String args[]) {		
		Task task = new Task();
		task.setStatus(TaskStatus.OPEN);
	}
	

}
