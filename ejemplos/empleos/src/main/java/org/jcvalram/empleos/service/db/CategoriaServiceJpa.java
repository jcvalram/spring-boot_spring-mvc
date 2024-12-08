package org.jcvalram.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.jcvalram.empleos.model.Categoria;
import org.jcvalram.empleos.repository.CategoriaRepository;
import org.jcvalram.empleos.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
//@Primary
public class CategoriaServiceJpa implements ICategoriasService {
	
	@Autowired
	private CategoriaRepository repository;

	public void guardar(Categoria categoria) {
		repository.save(categoria);
	}

	public List<Categoria> buscarTodas() {
		return repository.findAll();
	}

	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = repository.findById(idCategoria);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void eliminar(Integer idCategoria) {
		repository.deleteById(idCategoria);
	}

	public Page<Categoria> buscarTodas(Pageable page) {
		return repository.findAll(page);
	}

}
