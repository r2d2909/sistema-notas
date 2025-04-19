package com.proyecto.app.repositorio;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.modelo.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
}
