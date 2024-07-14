package com.aluradesafios.Literalura.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer anoNacimiento;
    private Integer anoMuerte;
@OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private List<Libros> libros = new ArrayList<>();

public Autor(){}

    public Autor(DatosAutores datosAutores, Libros libros){
    this.nombre= datosAutores.nombre();
    this.anoNacimiento = datosAutores.anoNacimiento();
    this.anoMuerte = datosAutores.anoMuerte();
    this.libros.add(libros);
    }

    public void addLibros(Libros libros){
    this.libros.add(libros);
    }

    public Autor(DatosAutores datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anoNacimiento = datosAutor.anoNacimiento();
        this.anoMuerte = datosAutor.anoMuerte();
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return """
                Autor; %s
                Fecha de nacimiento: %d
                Fecha de fallecimiento: %d
                Libros: %s
                """.formatted(this.nombre, this.anoNacimiento, this.anoMuerte,
                this.libros.stream().map(Libros::getTitulo).toList().toString()
        );
    }
}
