package com.tarea2.services;

import java.util.List;

import com.tarea2.entity.Proyecto;

public interface ProyectoService {
	
	public List<Proyecto> findAll();
	
	public Proyecto findById(Long id);
	
	public Proyecto save(Proyecto proyecto);
	
	public void deleteById(Long id);

}
