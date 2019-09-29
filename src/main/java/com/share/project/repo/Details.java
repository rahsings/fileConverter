package com.share.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.share.project.model.ViewClass;


public interface Details extends JpaRepository<ViewClass, Long>{

	@Query("SELECT e FROM ViewClass e")
	List<ViewClass> findAllName();
	
}
