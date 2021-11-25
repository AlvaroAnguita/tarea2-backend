package com.tarea2.dao;

import org.springframework.data.repository.CrudRepository;

import com.tarea2.entity.Proyecto;

public  interface ProyectoDao extends CrudRepository<Proyecto, Long> {

}
