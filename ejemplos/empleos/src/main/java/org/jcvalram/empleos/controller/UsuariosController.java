package org.jcvalram.empleos.controller;

import org.jcvalram.empleos.service.db.UsuarioServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioServiceJpa usuarioService;
	
    @GetMapping("/index")
	public String mostrarIndex(Model model) {
    	model.addAttribute("listUsuarios", usuarioService.buscarTodos());
    	return "usuarios/listUsuarios";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes redirectAttributes) {		    	
    	usuarioService.eliminar(idUsuario);
    	redirectAttributes.addFlashAttribute("msg", "Usuario eliminado");
		return "redirect:/usuarios/index";
	}

}
