package com.vc.kanbanProject.repository;

import com.vc.kanbanProject.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project,Integer> {
    Project findById(int project_id);
    List<Project> findByEmail(String email);
}
