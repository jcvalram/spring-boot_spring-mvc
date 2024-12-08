package org.jcvalram.jpa.demo.repository;

import org.jcvalram.jpa.demo.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
