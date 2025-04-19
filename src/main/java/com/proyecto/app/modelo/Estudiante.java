package com.proyecto.app.modelo;
import org.springframework.data.annotation.Transient;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estudiantes")
public class Estudiante {

    @Id
    private String id;

    private String tipoDocumento;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private String correoElectronico;
    private String numeroTelefonico;
    private String numeroRegistro;


    private int puntaje;
    private int comunicacionEscrita;
    private int razonamientoCuantitativo;
    private int lecturaCritica;
    private int competenciasCiudadanas;
    private int ingles;
    private int formulacionProyectosIngenieria;
    private int pensamientoCientifico;
    private int disenoSoftware;

    // ==== Getters y Setters ====
    
    	////♠DATOS PERSONALES ♠////
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getComunicacionEscrita() {
		return comunicacionEscrita;
	}

	public void setComunicacionEscrita(int comunicacionEscrita) {
		this.comunicacionEscrita = comunicacionEscrita;
	}

	public int getRazonamientoCuantitativo() {
		return razonamientoCuantitativo;
	}

	public void setRazonamientoCuantitativo(int razonamientoCuantitativo) {
		this.razonamientoCuantitativo = razonamientoCuantitativo;
	}

	public int getLecturaCritica() {
		return lecturaCritica;
	}

	public void setLecturaCritica(int lecturaCritica) {
		this.lecturaCritica = lecturaCritica;
	}

	public int getCompetenciasCiudadanas() {
		return competenciasCiudadanas;
	}

	public void setCompetenciasCiudadanas(int competenciasCiudadanas) {
		this.competenciasCiudadanas = competenciasCiudadanas;
	}

	public int getIngles() {
		return ingles;
	}

	public void setIngles(int ingles) {
		this.ingles = ingles;
	}

	public int getFormulacionProyectosIngenieria() {
		return formulacionProyectosIngenieria;
	}

	public void setFormulacionProyectosIngenieria(int formulacionProyectosIngenieria) {
		this.formulacionProyectosIngenieria = formulacionProyectosIngenieria;
	}

	public int getPensamientoCientifico() {
		return pensamientoCientifico;
	}

	public void setPensamientoCientifico(int pensamientoCientifico) {
		this.pensamientoCientifico = pensamientoCientifico;
	}

	public int getDisenoSoftware() {
		return disenoSoftware;
	}

	public void setDisenoSoftware(int disenoSoftware) {
		this.disenoSoftware = disenoSoftware;
	}

	
		/////♠METODOS CALCULO NIVEL DE LAS NOTAS♠ /////

	
	@Transient
	public List<List<String>> getResumenNotas() {
	    List<List<String>> resumen = new ArrayList<>();
	    List<Integer> notas = getNotasComoLista();
	    // Lista de nombres de materias en orden (ajústala según tu necesidad)
	    List<String> materias = Arrays.asList(
	        "Puntaje", "Comunicación Escrita", "Razonamiento Cuantitativo", "Lectura Crítica",
	        "Competencias Ciudadanas", "Inglés", "Formulación De Proyectos De Ingeniería",
	        "Pensamiento Científico -Matemáticas Y Estadística", "Diseño De Software"
	    );

	    for (int i = 0; i < notas.size(); i++) {
	        int nota = notas.get(i);
	        String materia = i < materias.size() ? materias.get(i) : "Materia " + (i + 1);
	        String nivel;
	        String color;

	        // Niveles según tu fórmula de Excel
	        if (nota >= 191 && nota <= 300) {
	            nivel = "Nivel 4";
	            color = "text-primary";
	        } else if (nota >= 156 && nota <= 190) {
	            nivel = "Nivel 3";
	            color = "text-info";
	        } else if (nota >= 126 && nota <= 155) {
	            nivel = "Nivel 2";
	            color = "text-warning";
	        } else if (nota >= 0 && nota <= 125) {
	            nivel = "Nivel 1";
	            color = "text-danger";
	        } else {
	            nivel = "Sin nivel";
	            color = "text-muted";
	        }

	        // Añadir la fila con: materia, nota, nivel, color
	        resumen.add(Arrays.asList(materia, String.valueOf(nota), nivel, color));
	    }

	    return resumen;
	}
	
	public List<Integer> getNotasComoLista() {
	    return Arrays.asList(
	        puntaje,
	        comunicacionEscrita,
	        razonamientoCuantitativo,
	        lecturaCritica,
	        competenciasCiudadanas,
	        ingles,
	        formulacionProyectosIngenieria,
	        pensamientoCientifico,
	        disenoSoftware
	    );
	}

	

}
