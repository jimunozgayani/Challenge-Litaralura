package com.aluradesafios.Literalura.modelos;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity

public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(columnDefinition ="varchar(500)", unique = true)
    private String titulo;
    private String idioma;
    private Integer contadorDeDescargas;
  @ManyToOne
   private Autor autores;

    public Libros(){

    }

    public Libros(String titulo,List<String> idioma, Integer contadorDeDescargas, List<DatosAutores>autores){
        this.titulo = titulo;
        this.idioma = idioma.get(0);
        this.contadorDeDescargas=contadorDeDescargas;
        this.autores = new Autor(autores.get(0));
        }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getContadorDeDescargas() {
        return contadorDeDescargas;
    }

    public void setContadorDeDescargas(Integer contadorDeDescargas) {
        this.contadorDeDescargas = contadorDeDescargas;
    }

    public Autor getAutor() {
        return autores;
    }

    public void setAutor(Autor autor) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return  """
                -----------------Libro---------------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                -------------------------------------
                """.formatted(this.titulo,this.autores.getNombre(),this.idioma,this.contadorDeDescargas);
    }
}

