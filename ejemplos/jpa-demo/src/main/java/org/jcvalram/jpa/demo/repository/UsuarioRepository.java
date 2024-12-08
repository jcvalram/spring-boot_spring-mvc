package org.jcvalram.jpa.demo.repository;

import org.jcvalram.jpa.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
