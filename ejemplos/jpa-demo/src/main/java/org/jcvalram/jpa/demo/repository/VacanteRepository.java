package org.jcvalram.jpa.demo.repository;

import java.util.List;

import org.jcvalram.jpa.demo.models.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacanteRepository extends JpaRepository<Vacante, Integer> {
	
	List<Vacante> findByEstatus(String estatus);
	
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado, String estatus);
	
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);
	
	List<Vacante> findByEstatusIn(String[] estatus);

}
