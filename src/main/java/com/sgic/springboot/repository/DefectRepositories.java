package com.sgic.springboot.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.springboot.model.Defect;

@Repository
public interface DefectRepositories extends JpaRepository<Defect, Long>{

	Page<Defect> findByProjectId(Long projectId, Pageable pageable);
    Optional<Defect> findByIdAndProjectId(Long id, Long projectId);
	
	
}
