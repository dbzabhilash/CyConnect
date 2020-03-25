package com.example.demo.repositories;
import com.example.demo.models.Login;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,Integer> {

	List<Login> findAll();
	@SuppressWarnings("unchecked")
	Login save(Login user);
	
	
	//List<Login> findByUsernameAndPass(String username, String password);

	
	@Query(value = "SELECT * FROM login WHERE username = ?1", nativeQuery = true)
	Login findByUsername(String username);
	
	@Query(value = "SELECT * FROM login WHERE password = ?1", nativeQuery = true)
	Login findByPassword(String password);
}
