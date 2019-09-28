package com.sgic.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.sgic.springboot.model.Defect;
import com.sgic.springboot.repository.DefectRepositories;
import com.sgic.springboot.repository.ProjectRepositories;

@RestController
@RequestMapping("/api/v1")
public class DefectController {
	@Autowired
	DefectRepositories defectRepositories;
	
	@Autowired
	ProjectRepositories projectRepositories;

	
	@GetMapping("/project/{projectId}/defect")
    public Page<Defect> getAllDefectsByProjectId(@PathVariable (value = "projectId") Long projectId,
                                                Pageable pageable) {
        return defectRepositories.findByProjectId(projectId, pageable);
    }
	
	
	@PostMapping("/project/{projectId}/defect")
    public Object createDefect(@PathVariable (value = "projectId") Long projectId,
                                 @Valid @RequestBody Defect defect) {
        return projectRepositories.findById(projectId).map(project -> {
            defect.setProject(project);
            return defectRepositories.save(defect);
        });
    }
	@DeleteMapping("/project/{projectId}/defect/{defectId}")
    public ResponseEntity<?> deleteDefect(@PathVariable Long projectId,
                                          @PathVariable Long defectId) {
        if(!projectRepositories.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        return defectRepositories.findById(defectId)
                .map(defect -> {
                	defectRepositories.delete(defect);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Defect not found with id " + defectId));

    }
	
//	@PutMapping("/project/{projectId}/defect/{defectId}")
//    public Defect updateProject(@PathVariable Long projectId,
//                               @PathVariable Long defectId,
//                               @Valid @RequestBody Defect defectRequest) {
//        if(!projectRepositories.existsById(projectId)) {
//            throw new ResourceNotFoundException("Project not found with id " + projectId);
//        }
//
//        return defectRepositories.findById(defectId)
//                .map(defect -> {
//                    defect.setProject(defectRequest.getProject());
//                    defect.setProject(defectRequest.getProject());
//                    defect.setProject(defectRequest.getProject());
//                    return defectRepositories.save(defect);
//                }).orElseThrow(() -> new ResourceNotFoundException("Defect not found with id " + defectId));
//    }

	
}

