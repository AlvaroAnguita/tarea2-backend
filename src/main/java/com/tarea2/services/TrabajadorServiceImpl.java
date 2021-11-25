package com.tarea2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tarea2.dao.TrabajadorDao;
import com.tarea2.entity.Trabajador;

@Service
public class TrabajadorServiceImpl implements TrabajadorService {

	@Autowired
	private TrabajadorDao trabajadorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		return (List<Trabajador>) trabajadorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findById(Long id) {
		return trabajadorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Trabajador save(Trabajador trabajador) {
		return trabajadorDao.save(trabajador);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		trabajadorDao.deleteById(id);
	}

}
