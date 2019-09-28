package com.sgic.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.springboot.exception.ResourceNotFoundException;
import com.sgic.springboot.model.Project;
import com.sgic.springboot.repository.ProjectRepositories;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {
	@Autowired
	ProjectRepositories projectRepositories;

	@PostMapping(value = "/project")
	public ResponseEntity<?> createNote(@RequestBody Project project) {
		projectRepositories.save(project);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	@GetMapping("/project")
	  public List<Project> getProject() {
		return projectRepositories.findAll();

	}
	

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long projectId) {
        return projectRepositories.findById(projectId)
                .map(project -> {
                	projectRepositories.delete(project);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }
    
    @PutMapping("/project/{projectId}")
    public Project updateQuestion(@PathVariable Long projectId,
                                   @Valid @RequestBody Project projectRequest) {
        return projectRepositories.findById(projectId)
                .map(project -> {
                	project.setProjectName(projectRequest.getProjectName());
                	project.setProjectDescription(projectRequest.getProjectDescription());
                    return projectRepositories.save(project);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }
}

