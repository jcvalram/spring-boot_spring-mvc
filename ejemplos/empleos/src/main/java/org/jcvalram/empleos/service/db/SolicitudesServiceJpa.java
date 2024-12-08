package org.jcvalram.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.jcvalram.empleos.model.Solicitud;
import org.jcvalram.empleos.repository.SolicitudesRepository;
import org.jcvalram.empleos.service.ISolicitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	@Autowired
	private SolicitudesRepository repository;

	public void guardar(Solicitud solicitud) {
		repository.save(solicitud);
	}

	public void eliminar(Integer idSolicitud) {
		repository.deleteById(idSolicitud);
	}

	public List<Solicitud> buscarTodas() {
		return repository.findAll();
	}

	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = repository.findById(idSolicitud);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public Page<Solicitud> buscarTodas(Pageable page) {
		return repository.findAll(page);
	}	

}
