package com.tarea2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	//loguear 
	@PostMapping("loginTrabajadores")
	public ResponseEntity<?> login(@RequestBody String email,@RequestBody String pass) {
		Trabajador t;
		Map<String,Object> response= new HashMap<>();
		
		try {
			t= trabajadorService.findByEP(email, pass);

		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar consulta en base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(t==null) {
			response.put("mensaje", "El trabajador ID: ".concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}else {
			if(t.isAdmin()) {
				response.put("mensaje","Soy admin");
				response.put("boleean", true);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
			}else
				response.put("mensaje","No soy admin");
				response.put("boleean", false);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}
		
	}
	
	//buscar trabajador
	@GetMapping("trabajadores/{id}")
	public ResponseEntity<?>showTrabajador(@PathVariable Long id){
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
			response.put("mensaje", "El trabajador ID: ".concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Trabajador>(t,HttpStatus.OK);
	}
	//crear trabajador
	@PostMapping("trabajadores")
	public ResponseEntity<?>createTrabajador(@RequestBody Trabajador trabajador){

		Map<String, Object> response= new HashMap<>();
		
		try {
			trabajadorService.save(trabajador);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El trabajador ha sido creado con éxito!");
		response.put("trabajador", trabajador);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	//actualizar trabajador
	@PutMapping("trabajdores/{id}")
	public ResponseEntity<?> update(@RequestBody Trabajador trabajador, @PathVariable Long id){
		Trabajador tActual=trabajadorService.findById(id);
		
		Map<String,Object> response= new HashMap<>();
		
		if(tActual==null) {
			response.put("mensaje","Error: no se pudo editar, el trabajador: ".concat(id.toString().concat("no existe el id en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			tActual.setDni(trabajador.getDni());
			tActual.setDepartamento(trabajador.getDepartamento());
			tActual.setCargo(trabajador.getCargo());
			tActual.setEstado(trabajador.getEstado());
			tActual.setApellidos(trabajador.getApellidos());
			tActual.setNombre(trabajador.getNombre());
			tActual.setEmail(trabajador.getEmail());
			tActual.setTelefono(trabajador.getTelefono());
			tActual.setDireccion(trabajador.getDireccion());
			tActual.setCargo(trabajador.getCargo());
			
			
			trabajadorService.save(tActual);
		} catch (DataAccessException e){
			response.put("mensaje","Error al actualizar el trabajador en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El Trabajador ha sido creado con éxito!");
		response.put("trabajador", tActual);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
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
		@GetMapping("/proyectos")
		public ResponseEntity<?> showProject(){
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
		
		@GetMapping("/proyectos/{id}")
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
