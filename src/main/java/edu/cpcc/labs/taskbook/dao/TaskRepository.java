package edu.cpcc.labs.taskbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.cpcc.labs.taskbook.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	// FIXME: fix this query...
	@Query(value= "select c.name from country c where c.continent = ?1", nativeQuery=true)
	List<Task> findTasksByStatus(String status);	

}
