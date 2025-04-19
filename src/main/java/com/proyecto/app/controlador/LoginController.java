package com.proyecto.app.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    // 🔒 Mostrar la página de login
    @GetMapping({"/", "/login"})
    public String login() {
        return "login"; // login.html en /templates
    }

    // ✅ Después del login, redirecciona según el rol del usuario
    @GetMapping("/postLogin")
    public String postLogin(Authentication auth) {
        boolean isCoordinador = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR"));

        if (isCoordinador) {
            return "redirect:/coordinador/lista"; // vista del coordinador
        } else {
            return "redirect:/estudiante/perfil"; // vista del estudiante
        }
    }
}
