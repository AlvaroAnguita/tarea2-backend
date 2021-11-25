package com.tarea2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoServiceImpl implements GrupoService {

	@Autowired
	private GrupoDao grupoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Grupo> findAll() {
		return List<Grupo> grupoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo findById(Long id) {
		return grupoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Grupo save(Grupo grupo) {
		return grupoDao.save(grupo);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		grupoDao.deleteById(id);
	}

}
