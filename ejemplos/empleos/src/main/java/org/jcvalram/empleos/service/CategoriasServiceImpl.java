package org.jcvalram.empleos.service;

import java.util.LinkedList;
import java.util.List;

import org.jcvalram.empleos.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
//@Primary
public class CategoriasServiceImpl implements ICategoriasService {
	
	private List<Categoria> categorias = null;
	
	public CategoriasServiceImpl() {
		categorias = new LinkedList<Categoria>();
		
		Categoria cat1 = new Categoria();
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		cat1.setDescripcion("Descripcion de la categoria Contabilidad");
		
		Categoria cat2 = new Categoria();
		cat2.setId(2);
		cat2.setNombre("Ventas");
		cat2.setDescripcion("Trabajos relacionados con Ventas");
		
		Categoria cat3 = new Categoria();
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcion("Trabajos relacionados con Counicaciones");
		
		Categoria cat4 = new Categoria();
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		cat4.setDescripcion("Trabajos de Arquitectura");
		
		Categoria cat5 = new Categoria();
		cat5.setId(5);
		cat5.setNombre("Educacion");
		cat5.setDescripcion("Maestros, tutores, etc");
		
		Categoria cat6 = new Categoria();
		cat6.setId(6);
		cat6.setNombre("Desarrollo de Software");
		cat6.setDescripcion("Trabajo para programadores");
		
		categorias.add(cat1);
		categorias.add(cat2);
		categorias.add(cat3);
		categorias.add(cat4);
		categorias.add(cat5);
		categorias.add(cat6);
	}

	public void guardar(Categoria categoria) {
		categorias.add(categoria);
	}

	public List<Categoria> buscarTodas() {
		return categorias;
	}

	public Categoria buscarPorId(Integer idCategoria) {
		return categorias.stream()
			.filter(c -> c.getId().equals(idCategoria))
			.findFirst().get();
	}

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
}
