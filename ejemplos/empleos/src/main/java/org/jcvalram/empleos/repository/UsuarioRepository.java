package org.jcvalram.empleos.repository;

import org.jcvalram.empleos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String userName);

}
