package com.tarea2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tarea2.dao.ProyectoDao;
import com.tarea2.entity.Proyecto;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	@Autowired
	private ProyectoDao proyectoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> findAll() {
		return (List<Proyecto>) proyectoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Proyecto findById(Long id) {
		return proyectoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Proyecto save(Proyecto proyecto) {
		return proyectoDao.save(proyecto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		proyectoDao.deleteById(id);
	}

}
