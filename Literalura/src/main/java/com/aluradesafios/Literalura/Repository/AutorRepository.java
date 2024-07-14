package com.aluradesafios.Literalura.Repository;

import com.aluradesafios.Literalura.modelos.Autor;
import com.aluradesafios.Literalura.modelos.Libros;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE a.nombre ILIKE %:nombreAutor%")
    List<Libros> tituloPorAutor(String nombreAutor);

    @Query("SELECT l FROM Libros l")
    List<Libros> todosLosLibros();

    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento <= :year AND a.anoMuerte >= :year")
    List<Autor> anoEspecificoAutorVivo(Integer year);

        @Query("SELECT l FROM Libros l WHERE l.idioma =:acronimo")
    List<Libros> listaLibrosPorIdioma(String acronimo);

    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);


//   @Query("SElECT l FROM Libro l WHERE l.titulo = %:nombreTitulo%")
//  Optional<Libros> buscarTituloPorNombre(String nombreTitulo) ;


    @Query("SELECT l FROM Libros l ORDER BY l.contadorDeDescargas DESC LIMIT 10")
    List<Libros> masDescargados();

}
