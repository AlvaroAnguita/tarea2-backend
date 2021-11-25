package com.tarea2.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tarea2.enums.Estado;


@Entity
@Table
public class Proyecto implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5975934858668439418L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String cliente;
	
	@Column(nullable = false)
	private Estado estado;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "grupo_id")
	private int grupoId;
	
	@OneToOne(mappedBy = "proyecto")
	private Grupo grupo;
	
	
}
