package org.jcvalram.empleos.controller;

import org.jcvalram.empleos.model.Categoria;
import org.jcvalram.empleos.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	@Qualifier(value = "categoriaServiceJpa")
	private ICategoriasService categoriasService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("listCategorias", categoriasService.buscarTodas());
		return "categorias/listaCategorias";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPageable(Pageable page, Model model) {
		model.addAttribute("listCategorias", categoriasService.buscarTodas(page));
		return "categorias/listaCategorias";
	}
	
	@GetMapping("/create")
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") int id, Model model) {
		Categoria categoria = categoriasService.buscarPorId(id);
		model.addAttribute("categoria", categoria);
		return "categorias/detalle";
	}
	
	@PostMapping("/save")
	public String guardar(Categoria categoria, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "categorias/formCategoria";
		}
		categoriasService.guardar(categoria);
		// Redirige atributos hacia una llamada de redirección
		redirectAttributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/categorias/index";
	}
	
	@DeleteMapping("/delete/{id}")
	public void eliminar(@PathVariable("id") int id) {
		categoriasService.eliminar(id);
	}
}
