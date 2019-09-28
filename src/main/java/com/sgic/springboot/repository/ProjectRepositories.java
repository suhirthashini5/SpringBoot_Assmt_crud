package com.sgic.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.springboot.model.Project;

@Repository
public interface ProjectRepositories extends JpaRepository<Project, Long>{
	
	
}
