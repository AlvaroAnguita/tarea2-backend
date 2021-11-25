package com.tarea2.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarea2.entity.Grupo;
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

	@PostMapping("trabajadores/{id}/grupos")
	public ResponseEntity<?> crearGrupo(@PathVariable Long id, @RequestBody Grupo grupo) {
		HashMap<String,Object> response = new HashMap<>();
		Trabajador trabajador = null;

		try {
			trabajador = trabajadorService.findById(id);
			if (trabajador == null) {
				response.put("error", "El trabajador no existe.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			if (trabajador.isAdmin()) {
				grupoService.save(grupo);
			} else {
				response.put("error", "El trabajador no tiene los permisos necesarios para crear un nuevo grupo.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.UNAUTHORIZED);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar los datos en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("grupos")
	public ResponseEntity<?> getGrupos() {
		HashMap<String,Object> response = new HashMap<>();
		List<Grupo> grupos = new LinkedList<>();

		try {
			grupos = grupoService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Grupo>>(grupos, HttpStatus.OK);
	}

	@GetMapping("trabajadores/{id}/grupos")
	public ResponseEntity<?> getGruposById(@PathVariable Long id) {
		Trabajador trabajador = null;
		List<Grupo> grupos = new LinkedList<>();
		List<Grupo> gruposTrabajador = new LinkedList<>();
		HashMap<String,Object> response = new HashMap<>();

		try {
			trabajador = trabajadorService.findById(id);
			if (trabajador == null) {
				response.put("error", "El trabajador no existe.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			grupos = grupoService.findAll();
			for (Grupo grupo : grupos) {
				if (!grupo.getTrabajadores().contains(trabajador)) {
					gruposTrabajador.add(grupo);
				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Grupo>>(gruposTrabajador, HttpStatus.OK);
	}

	@GetMapping("trabajadores/{trabajador_id}/grupos/{grupo_id}")
	public ResponseEntity<?> getGrupoById(@PathVariable Long trabajador_id, @PathVariable Long grupo_id) {
		Trabajador trabajador = null;
		Grupo grupo = null;
		HashMap<String,Object> response = new HashMap<>();

		try {
			trabajador = trabajadorService.findById(trabajador_id);
			if (trabajador == null) {
				response.put("error", "El trabajador no existe.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			grupo = grupoService.findById(grupo_id);
			if (grupo == null) {
				response.put("error", "El grupo no existe.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			if (!grupo.getTrabajadores().contains(trabajador)) {
				response.put("error", "El trabajador especificado no pertenece al grupo.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
	}

	@PutMapping("trabajadores/{admin_id}/grupos/{grupo_id}")
	public ResponseEntity<?> editGrupoById(@PathVariable Long admin_id, @PathVariable Long grupo_id, @RequestBody Grupo grupo) {
		Grupo grupoAct = null;
		Trabajador trabajador = null;
		HashMap<String,Object> response = new HashMap<>();
		
		try {
			trabajador = trabajadorService.findById(admin_id);
			if (trabajador == null) {
				response.put("error", "El trabajador no existe.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			if (trabajador.isAdmin()) {
				grupoAct = grupoService.findById(grupo_id);
				if (grupoAct == null) {
					response.put("error", "El grupo especificado no existe.");
					return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
				}
				if (grupo.getNombre() != null && !grupo.getNombre().isEmpty()) {
					grupoAct.setNombre(grupo.getNombre());
				}
				if (grupo.getProyecto() != null && !grupo.getProyecto().getNombre().isEmpty()) {
					grupoAct.setProyecto(grupo.getProyecto());
				}
				if (grupo.getTrabajadores() != null && !grupo.getTrabajadores().isEmpty()) {
					List<Trabajador> merged = new LinkedList<Trabajador>(grupoAct.getTrabajadores());
					merged.addAll(grupo.getTrabajadores());
				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al editar el grupo.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
	}

}
