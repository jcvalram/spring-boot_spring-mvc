package org.jcvalram.empleos.service.db;

import java.util.List;

import org.jcvalram.empleos.model.Usuario;
import org.jcvalram.empleos.repository.UsuarioRepository;
import org.jcvalram.empleos.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceJpa implements IUsuariosService {

	@Autowired
	private UsuarioRepository repository;
	
	public void guardar(Usuario usuario) {
		repository.save(usuario);
	}

	public void eliminar(Integer idUsuario) {
		repository.deleteById(idUsuario);
	}

	public List<Usuario> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public Usuario buscarPorUserName(String userName) {
		return repository.findByUsername(userName);
	}

}
