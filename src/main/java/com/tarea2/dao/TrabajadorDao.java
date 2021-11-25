package com.tarea2.dao;

import org.springframework.data.repository.CrudRepository;

import com.tarea2.entity.Trabajador;

public interface TrabajadorDao extends CrudRepository<Trabajador, Long> {

}
