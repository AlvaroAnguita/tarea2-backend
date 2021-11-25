package com.tarea2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarea2.entity.Proyecto;
import com.tarea2.entity.Trabajador;
import com.tarea2.services.GrupoService;
import com.tarea2.services.ProyectoService;
import com.tarea2.services.TrabajadorService;


@RestController
@RequestMapping("/api")
public class ApiRestController {
	
	@Autowired
	private TrabajadorService trabajadorService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private ProyectoService proyectoService;
	
	
	@GetMapping("trabajadores")
	public List<Trabajador> listaTrabajadores() {
		return trabajadorService.findAll();
	}
	
	@GetMapping("trabajadores/{id}")
	public ResponseEntity<?>show(@PathVariable Long id){
		Trabajador t= null;
		Map<String,Object> response= new HashMap<>();
		
		try {
			t= trabajadorService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar consulta en base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(t==null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Trabajador>(t,HttpStatus.OK);
	}
	
	//CRUD
	//POST Project
	@PostMapping("/proyectos")
	public ResponseEntity<?> createProject(@RequestBody Proyecto proyecto) {
		Map<String, Object> response = new HashMap<>();
			try {
				proyectoService.save(proyecto);

			} catch (DataAccessException e) {
				response.put("Mensaje", "No se puede crear el proyecto");
				response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("Existoso", "Se ha creado el proyecto correctamente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}	
	
	
	//SHOW Projects
	@GetMapping
	public ResponseEntity<?> showProject(@RequestParam Proyecto proyecto){
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			proyectoService.findAll();
		} catch (DataAccessException e) {
			response.put("Error", "No se puede mostrar los proyectos");
			response.put("Incorrecto", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Existoso", "Se muestran los proyectos");
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> showProjectById(@PathVariable Long id){
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			proyectoService.findById(id);
		} catch (DataAccessException e) {
			response.put("Error", "No se puede mostrar el proyecto con id: "+id);
			response.put("Incorrecto", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	
		}
		response.put("Exitoso", "Se muestra proyecto con id: "+id);
		return new ResponseEntity<Map<String,Object>> (response, HttpStatus.OK);
		
		
	}
	
	
}
	
	


