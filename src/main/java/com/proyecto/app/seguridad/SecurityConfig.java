package com.proyecto.app.seguridad;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.proyecto.app.repositorio.UsuarioRepository;
import com.proyecto.app.modelo.Usuario;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository userRepo;

    	// 1) Bean para encriptar/validar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    	// 2) Bean que carga el UserDetailsService a partir de tu repo de Usuario
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            	// Busca el Usuario por username (que en tu modelo es @Id)
            Usuario u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            	// Convierte Set<String> roles en GrantedAuthority, anteponiendo "ROLE_"
            var authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());

            	// Devuelve el UserDetails de Spring Security
            return new User(u.getUsername(), u.getPassword(), authorities);
        };
    }

    // 3) Bean que define las reglas de seguridad HTTP (rutas, login, logout)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
              // Rutas públicas
              .requestMatchers("/", "/login", "/css/**").permitAll()
              //creacion del coordinador 0
              .requestMatchers("/coordinador/registro", "/coordinador/guardar").permitAll()
              // Solo ROLE_COORDINADOR puede /coordinador/**
              .requestMatchers("/coordinador/**").hasRole("COORDINADOR")
              // Solo ROLE_ESTUDIANTE puede /estudiante/**
              .requestMatchers("/estudiante/**").hasRole("ESTUDIANTE")
              // El resto requiere autenticación
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginPage("/login")               // tu propia vista login.html
              .permitAll()                       // permitir a cualquiera verla
              .defaultSuccessUrl("/postLogin", true) // tras login va a /postLogin
          )
          .logout(logout -> logout
              .permitAll()                       // permitir logout a cualquiera
          );
        return http.build();
    }
}
