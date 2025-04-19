package com.proyecto.app.controlador;

import com.proyecto.app.modelo.Estudiante;
import com.proyecto.app.repositorio.EstudianteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepo;

    // 🟢 Mostrar el perfil del estudiante autenticado
    @GetMapping("/estudiante/perfil")
    public String perfilEstudiante(Authentication auth, Model model) {
        String username = auth.getName(); // ← el username es la identificación del estudiante

        Estudiante estudiante = estudianteRepo.findById(username).orElse(null);
        model.addAttribute("estudiante", estudiante);

        return "estudiante/perfilEstudiante"; // debes crear esta vista
    }
}
