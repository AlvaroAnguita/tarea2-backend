package com.tarea2.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tarea2.entity.Trabajador;

public interface TrabajadorDao extends CrudRepository<Trabajador, Long> {
	
	@Query("select * from trabajadores where email=:email and pass =:pass ")
	Trabajador findyByEmailAndPass(@Param ("email") String email,@Param ("pass")String pass);
	
	@Query("select * from trabajadores where email=:email ")
	Trabajador findyByEmail(@Param ("email") String email);
	
	
	
	
}
