package org.jcvalram.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jcvalram.empleos.model.Perfil;
import org.jcvalram.empleos.model.Usuario;
import org.jcvalram.empleos.model.Vacante;
import org.jcvalram.empleos.service.ICategoriasService;
import org.jcvalram.empleos.service.IUsuariosService;
import org.jcvalram.empleos.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier(value = "categoriaServiceJpa")
	private ICategoriasService categoriasService;
	
	@Autowired
	private IVacantesService vacantesService;
	
	@Autowired
   	private IUsuariosService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		return "home";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "/usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes redirectAttributes) {
		String pwdPlano = usuario.getPassword();
		String pwdEncr = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncr);
		
		Perfil perfil = new Perfil();
		perfil.setId(3);
		perfil.setPerfil("USUARIO");
		usuario.agregar(perfil);
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		
		usuarioService.guardar(usuario);
		redirectAttributes.addFlashAttribute("msg", "Usuario guardado");
		
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/tabla")
	public String mosrtarTabla(Model model) {
		List<Vacante> lista = vacantesService.buscarTodas();
		model.addAttribute("lista", lista);
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a Intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		model.addAttribute("lista", lista);
		return "listado";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por: " + vacante);
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				// Where descripcion like %?%
				.withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		
		// Prepara un objeto de muestra a partir de otro
		// Para realizar busqueda en JPA por los campos de la clase
		// que contienen información
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = vacantesService.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario:" + username);
		
		auth.getAuthorities().forEach(g -> System.out.println("ROL: " + g.getAuthority()));
		
		if (session.getAttribute("usuario") == null) {
			Usuario usuario = usuarioService.buscarPorUserName(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		
		return "redirect:/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Inicializa a null los atributos de tipo String que 
		// vienen vacíos
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	// Anotación que sirve para compartir los datos en el model
	// para todas las peticiones del controlador
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacante = new Vacante();
		vacante.reset();
		model.addAttribute("search", vacante);
		model.addAttribute("vacantes", vacantesService.buscarDestacadas());
		model.addAttribute("categorias", categoriasService.buscarTodas());
	}
	
	@GetMapping("/home")
	public String mostrarHome2(Model model) {
		String nombre = "Auxiliar de Contabilidad";
		Date fechaPub = new Date();
		double salario = 9000.0;
		boolean vigente = true;
		
		model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaPub);
		model.addAttribute("salario", salario);
		model.addAttribute("vigente", vigente);
		return "home2";
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable String texto) {
		return texto + " Encriptado en Bcrypt:" + passwordEncoder.encode(texto);
	}
}
