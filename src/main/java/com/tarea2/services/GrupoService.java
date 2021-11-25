package com.tarea2.services;

import java.util.List;

import com.tarea2.entity.Grupo;

public interface GrupoService {
	
	public List<Grupo> findAll();

	public Grupo findById(Long id);
	
	public Grupo save(Grupo grupo);
	
	public void deleteById(Long id);
	
}
