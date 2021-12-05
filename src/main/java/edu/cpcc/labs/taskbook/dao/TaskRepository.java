package edu.cpcc.labs.taskbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.cpcc.labs.taskbook.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	@Query(value= "select t.name from task t where t.status = ?1", nativeQuery=true)
	List<Task> findTasksByStatus(String status);	

}
