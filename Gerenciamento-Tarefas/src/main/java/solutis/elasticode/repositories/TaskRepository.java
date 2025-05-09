package solutis.elasticode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solutis.elasticode.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
