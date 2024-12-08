package org.jcvalram.empleos.repository;

import org.jcvalram.empleos.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
