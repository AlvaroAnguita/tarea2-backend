package com.tarea2.entity;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.tarea2.enums.Activo;

@Entity
@Table(name = "trabajadores")
public class Trabajador implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true)
	private String dni;
	
	@Column(nullable = false)
	private String pass;
	
	private String nombre;
	
	private String apellidos;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(unique = true)
	private int telefono;
	
	private String direccion;
	
	private String departamento;
	
	private String cargo;
	
	@Column(nullable = false)
	private Activo estado;
	
	@Column(nullable = false)
	private boolean admin;
	
	@ManyToMany
	private LinkedList<Grupo> grupos;
	
	
	public Trabajador() {
		this.admin=false;
		this.estado=estado.ACTIVO;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Activo getEstado() {
		return estado;
	}

	public void setEstado(Activo estado) {
		this.estado = estado;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public LinkedList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(LinkedList<Grupo> grupos) {
		this.grupos = grupos;
	}

	private static final long serialVersionUID = -4452527777282732761L;
}
