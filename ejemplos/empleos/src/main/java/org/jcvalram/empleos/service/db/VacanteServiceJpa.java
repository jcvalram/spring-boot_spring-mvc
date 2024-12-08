package org.jcvalram.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.jcvalram.empleos.model.Vacante;
import org.jcvalram.empleos.repository.VacanteRepository;
import org.jcvalram.empleos.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class VacanteServiceJpa implements IVacantesService {

	@Autowired
	private VacanteRepository repository;
	
	public List<Vacante> buscarTodas() {
		return repository.findAll();
	}

	public Vacante buscarPorId(Integer id) {
		Optional<Vacante> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void guardar(Vacante vacante) {
		repository.save(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return repository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return repository.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return repository.findAll(page);
	}

}
