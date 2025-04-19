package com.proyecto.app.controlador;

import com.proyecto.app.modelo.Estudiante;
import com.proyecto.app.modelo.Usuario;
import com.proyecto.app.repositorio.EstudianteRepository;
import com.proyecto.app.repositorio.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //formulario para la creacion del usuario coordinador 
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (usuarioRepo.count() > 0) {
            // Si ya hay usuarios, no se puede acceder al formulario
            return "redirect:/login";
        }
        model.addAttribute("usuario", new Usuario());
        return "coordinador/registroCoordinador";
    }
    @PostMapping("/guardar")
    public String guardarCoordinador(@ModelAttribute Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(Collections.singleton("COORDINADOR"));
        usuarioRepo.save(usuario);
        return "redirect:/login";
    }


    // 🟢 Mostrar lista de estudiantesq
    @GetMapping("/lista")
    public String listarEstudiantes(Model model) {
        model.addAttribute("listaEstudiantes", estudianteRepo.findAll());
        return "coordinador/listaEstudiantes";
    }

    // 🟢 Mostrar formulario para nuevo estudiante
    @GetMapping("/formEstudiante")
    public String nuevoEstudiante(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "coordinador/formEstudiante";
    }

    // 🟢 Guardar estudiante (nuevo o editado)
    @PostMapping("/guardarEstudiante")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante) {
        estudianteRepo.save(estudiante);
        
        // Si no existe usuario, lo creamos (para login)
        if (!usuarioRepo.existsById(estudiante.getId())) {
            Usuario usuario = new Usuario();
            usuario.setUsername(estudiante.getId()); // mismo que el id del estudiante
            usuario.setPassword(passwordEncoder.encode("1234")); // contraseña genérica (se puede cambiar después)
            usuario.setRoles(Collections.singleton("ESTUDIANTE"));
            usuarioRepo.save(usuario);
        }

        return "redirect:/coordinador/lista";
    }

    // 🟢 Editar estudiante por ID
    @GetMapping("/editar/{id}")
    public String editarEstudiante(@PathVariable String id, Model model) {
        Optional<Estudiante> estudianteOpt = estudianteRepo.findById(id);
        if (estudianteOpt.isPresent()) {
            model.addAttribute("estudiante", estudianteOpt.get());
            return "coordinador/formEstudiante";
        } else {
            return "redirect:/coordinador/lista";
        }
    }

    // 🟢 Eliminar estudiante
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable String id) {
        estudianteRepo.deleteById(id);
        usuarioRepo.deleteById(id); // también eliminamos su usuario
        return "redirect:/coordinador/lista";
    }
}
