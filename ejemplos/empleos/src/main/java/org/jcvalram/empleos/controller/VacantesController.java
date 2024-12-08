package org.jcvalram.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jcvalram.empleos.model.Vacante;
import org.jcvalram.empleos.service.ICategoriasService;
import org.jcvalram.empleos.service.IVacantesService;
import org.jcvalram.empleos.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Autowired
	private IVacantesService vacantesService;
	
	@Autowired
	@Qualifier(value = "categoriaServiceJpa")
	private ICategoriasService categoriasService;
	
	@Value("${application.ruta.img}")
	private String rutaImg;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("listaCategorias", categoriasService.buscarTodas());
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("listVacantes", vacantesService.buscarTodas());
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = vacantesService.buscarTodas(page);
		model.addAttribute("listVacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/view/{id}")
	public String detalle(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = vacantesService.buscarPorId(idVacante);
		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/detalle";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		return "vacantes/formVacante";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = vacantesService.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes redirectAttributes,
			 @RequestParam("archivoImagen") MultipartFile multiPart) {
		// Control de error de tipo de datos al introducirlos
		// en el formulario. Ej: Tipo int y se mete un valor alfanumérico
		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utilities.guardarArchivo(multiPart, rutaImg);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}
		
		vacantesService.guardar(vacante);
		// Redirige atributos hacia una llamada de redirección
		redirectAttributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes redirectAttributes) {
		System.out.println("Borrando vacante con id:" + idVacante);
		vacantesService.eliminar(idVacante);
		redirectAttributes.addFlashAttribute("msg", "La vacante fue eliminada");
		return "redirect:/vacantes/index";
	}
}
