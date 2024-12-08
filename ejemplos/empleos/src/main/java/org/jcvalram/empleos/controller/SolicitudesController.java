package org.jcvalram.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jcvalram.empleos.model.Solicitud;
import org.jcvalram.empleos.model.Vacante;
import org.jcvalram.empleos.service.ISolicitudesService;
import org.jcvalram.empleos.service.IVacantesService;
import org.jcvalram.empleos.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	@Autowired
	private ISolicitudesService solicitudService;
	
	@Autowired
	private IVacantesService vacantesService;
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
		
    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex(Model model) {
    	List<Solicitud> listaSolicitudes = solicitudService.buscarTodas();
    	model.addAttribute("listaSolicitudes", listaSolicitudes);
		return "solicitudes/listSolicitudes";
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Pageable pageable, Model model) {
		Page<Solicitud> lista = solicitudService.buscarTodas(pageable);
		model.addAttribute("listaSolicitudes", lista);
		return "solicitudes/listSolicitudes";
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear(@PathVariable("idVacante") Integer idVacante, Model model) {
		Vacante vacante = vacantesService.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, RedirectAttributes redirectAttributes, 
			@RequestParam("archivoCV") MultipartFile multiPart) {
		
		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			return "solicitudes/formSolicitud";
		}

		if (!multiPart.isEmpty()) {
			String archivo = Utilities.guardarArchivo(multiPart, ruta);
			if (archivo != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				solicitud.setArchivo(archivo);
			}
		}
		solicitudService.guardar(solicitud);
		redirectAttributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/solicitudes/index";
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idSolicitud) {
		solicitudService.eliminar(idSolicitud);
		return "redirect:/solicitudes/indexPaginate";
	}
	
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
