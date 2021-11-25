package com.tarea2.entity;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Grupo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String nombre;
	
	@ManyToMany(mappedBy = "grupos")
	private LinkedList<Trabajador> trabajadores;
	
	@OneToOne
	@JoinColumn(name = "proyecto_id")
	private Proyecto proyecto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LinkedList<Trabajador> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(LinkedList<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	private static final long serialVersionUID = -5358821999906949372L;
	
}
