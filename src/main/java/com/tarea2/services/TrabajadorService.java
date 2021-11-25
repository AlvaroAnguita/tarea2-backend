package com.tarea2.services;

import java.util.LinkedList;
import java.util.List;

import com.tarea2.entity.Grupo;
import com.tarea2.entity.Trabajador;

public interface TrabajadorService {
	
	public List<Trabajador> findAll();
		
	public Trabajador findById(Long id);
	
	public Trabajador findByEP(String email, String pass);
	
	public Trabajador findByE(String email);
	
	public Trabajador save(Trabajador trabajador);
	
	public void deleteById(Long id);
	

	
}
