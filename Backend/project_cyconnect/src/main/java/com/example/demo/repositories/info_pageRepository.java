package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.info_page;

public interface info_pageRepository extends JpaRepository<info_page,Integer> {
	List<info_page> findAll();
	
	@SuppressWarnings("unchecked")
	info_page save(info_page dormDeets);
	
	@Query(value = "SELECT * FROM info_page WHERE place_name = ?1", nativeQuery = true)
	info_page findByDorm(String dorm);
	
//	@Query(value = "SELECT COUNT(*)\r\n FROM info_page", nativeQuery = true)
//	info_page findMaxID(String dorm);
	
	@Query(value = "SELECT * FROM info_page WHERE place_id = ?1", nativeQuery = true)
	info_page findByPlace_id(int id);
	
	@Query(value = "SELECT * FROM info_page WHERE study_eat_dorm = ?1", nativeQuery = true)
	List<info_page> findByTypeofPlace(int id);

}
