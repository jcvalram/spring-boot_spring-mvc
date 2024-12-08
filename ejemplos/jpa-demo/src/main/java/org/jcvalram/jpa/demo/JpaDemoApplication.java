package org.jcvalram.jpa.demo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.jcvalram.jpa.demo.models.Categoria;
import org.jcvalram.jpa.demo.models.Perfil;
import org.jcvalram.jpa.demo.models.Usuario;
import org.jcvalram.jpa.demo.models.Vacante;
import org.jcvalram.jpa.demo.repository.CategoriaRepository;
import org.jcvalram.jpa.demo.repository.PerfilRepository;
import org.jcvalram.jpa.demo.repository.UsuarioRepository;
import org.jcvalram.jpa.demo.repository.VacanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private VacanteRepository vacanteRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarVacantesVariosEstatus();
	}
	
	/**
	 * Buscar vacantes por varios estatus
	 */
	public void buscarVacantesVariosEstatus() {
		String[] estatus = new String[] {"Eliminada", "Creada"};
		vacanteRepository.findByEstatusIn(estatus)
			.forEach(v -> System.out.println(v.getId() + ": " 
					+ v.getNombre() + ": " + v.getEstatus()));
	}
	
	/**
	 * Busca vacantes por rango de salarios
	 */
	public void buscarVacantesSalario() {
		vacanteRepository.findBySalarioBetweenOrderBySalarioDesc(7000, 14000)
			.forEach(v -> System.out.println(v.getId() + ": " 
					+ v.getNombre() + ": " + v.getSalario()));
	}
	
	/**
	 * Query: Buscar vacantes por destacado y estatus
	 */
	public void buscarVacantesPorDestacadoEstadus() {
		vacanteRepository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada")
		.forEach(v -> System.out.println(v.getId() + ": " 
		+ v.getNombre() + ": " + v.getEstatus()));
	}
	
	/**
	 * Query: Buscar vacantes por estatus
	 */
	public void buscarVacantesPorEstatus() {
		vacanteRepository.findByEstatus("Aprobada")
			.forEach(v -> System.out.println(v.getId() + ": " 
			+ v.getNombre() + ": " + v.getEstatus()));
	}
	
	/**
	 * Busca un usuario y muestra sus perfiles
	 */
	public void buscarUsuario() {
		Optional<Usuario> optional = usuarioRepository.findById(1);
		if (optional.isPresent()) {
			Usuario user = optional.get();
			System.out.println("Usuario: " + user.getUsername());
			System.out.println("Perfiles asignados");
			user.getPerfiles().forEach(p -> System.out.println(p.getPerfil()));
		}
		else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	/**
	 * Crear un usuario con perfiles ADMINISTRADOR y USUARIO
	 */
	private void crearUsuarioConDosPerfiles() {
		Usuario user = new Usuario();
		user.setNombre("Jan Carlo");
		user.setEmail("jcvalram@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("jcvalram");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil perf1 = new Perfil();
		perf1.setId(2);
		
		Perfil perf2 = new Perfil();
		perf2.setId(3);
		
		user.agregar(perf1);
		user.agregar(perf2);
		
		usuarioRepository.save(user);
	}
	
	/**
	 * Crea perfiles
	 */
	private void crearPerfiles() {
		perfilRepository.saveAll(getPerfilesAplicacion());
	}
	
	private void buscarVacantes() {
		List<Vacante> list =vacanteRepository.findAll();
		list.forEach(v -> System.out.println(v.getId() + " " + v.getNombre()
		+ " --> " + v.getCategoria().getNombre()));
	}
	
	private void existeId() {
		boolean existe = categoriaRepository.existsById(30);
		System.out.println("La categoria existe:" + existe); 
	}
	
	private void buscarTodos() {
		categoriaRepository.findAll().forEach(c -> System.out.println(c));
	}
	
	/**
	 * findAll con paginación
	 */
	private void buscarTodosPaginado() {
		Page<Categoria> pagina = categoriaRepository.findAll(PageRequest.of(0, 5));
		System.out.println("Total registros:" + pagina.getTotalElements());
		System.out.println("Total paginas:" + pagina.getTotalPages());
		pagina.stream().forEach(c -> System.out.println(c));
	}
	
	/**
	 * findAll con paginación ordenada 
	 */
	private void buscarTodosPaginacionOrdenados() {
		Page<Categoria> pagina = categoriaRepository.findAll(PageRequest.of(0, 5, Sort.by("nombre")));
		System.out.println("Total registros:" + pagina.getTotalElements());
		System.out.println("Total paginas:" + pagina.getTotalPages());
		pagina.stream().forEach(c -> System.out.println(c.getId() + " " + c.getNombre()));
	}
	
	private void buscarTodosOrdenados() {
		categoriaRepository.findAll(Sort.by("nombre").descending())
			.forEach(c -> System.out.println(c));
	}
	
	private Categoria buscarPorId(int id) {
		Categoria categoria = null;
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (optional.isPresent()) {
			categoria = optional.get();
			System.out.println(categoria);
		}
		else {
			categoria = optional.orElse(new Categoria());
			System.out.println("Categoria no encontrada");
		}
		return categoria;
	}
	
	private void encontrarPorIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> respuesta = categoriaRepository.findAllById(ids);
		respuesta.forEach(System.out::print);
	}
	
	private void guardar() {
		Categoria categoria = new Categoria();
		categoria.setNombre("finanzas");
		categoria.setDescripcion("Trabajos realacionados con finanzas y contabilidad");
		categoriaRepository.save(categoria);
		System.out.println(categoria);
	}
	
	private void guardarTodas() {
		categoriaRepository.saveAll(getListaCategorias());
	}
	
	private void modificar() {
		Categoria categoria = buscarPorId(2);
		categoria.setNombre("Ing. de software");
		categoria.setDescripcion("Desarrollo de sistemas");
		categoriaRepository.save(categoria);
	}
	
	private void eliminar(int id) {
		categoriaRepository.deleteById(id);
	}
	
	private void eliminarTodos() {
		categoriaRepository.deleteAll();
	}
	
	private void eliminarTodosEnBloque() {
		categoriaRepository.deleteAllInBatch();
	}
	
	private void conteo() {
		long count = categoriaRepository.count();
		System.out.println("Total categorias: " + count);
	}
	
	private void guardarVacante() {
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de matemáticas");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de matemáticas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.0);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		Categoria categoria = new Categoria();
		categoria.setId(15);
		vacante.setCategoria(categoria);
		vacante.setDetalles("<h1>Los requisitos para profesor de Matemáticas");
		vacanteRepository.save(vacante);
	}
	
	private List<Categoria> getListaCategorias() {
		List<Categoria> lista = new LinkedList<Categoria>();
		
		Categoria c1 = new Categoria();
		c1.setNombre("Programador de Blockhain");
		c1.setDescripcion("Trabajo relacionado con Bicoins y Criptomonedas");
		
		Categoria c2 = new Categoria();
		c2.setNombre("Soldador/Pintura");
		c2.setDescripcion("Trabajos relacionados con soldadura y pintura");
		
		Categoria c3 = new Categoria();
		c3.setNombre("Ingeniero Industrial");
		c3.setDescripcion("Trabajos relacionados con Ingeniería industrial");
		
		lista.add(c1);
		lista.add(c2);
		lista.add(c3);
		return lista;
	}
	
	private List<Perfil> getPerfilesAplicacion() {
		List<Perfil> perfiles = new LinkedList<>();
		
		Perfil p1 = new Perfil();
		p1.setPerfil("SUPERVISOR");
		
		Perfil p2 = new Perfil();
		p2.setPerfil("ADMINISTRADOR");
		
		Perfil p3 = new Perfil();
		p3.setPerfil("USUARIO");
		
		perfiles.add(p1);
		perfiles.add(p2);
		perfiles.add(p3);
		
		return perfiles;
	}

}
