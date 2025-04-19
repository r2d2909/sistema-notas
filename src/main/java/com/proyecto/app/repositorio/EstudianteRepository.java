package com.proyecto.app.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.modelo.Estudiante;

public interface EstudianteRepository extends MongoRepository<Estudiante, String> {

}
