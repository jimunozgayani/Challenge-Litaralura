package com.aluradesafios.Literalura.Principal;

import com.aluradesafios.Literalura.Repository.AutorRepository;
import com.aluradesafios.Literalura.modelos.Autor;
import com.aluradesafios.Literalura.modelos.Datos;
import com.aluradesafios.Literalura.modelos.DatosLibros;
import com.aluradesafios.Literalura.modelos.Libros;
import com.aluradesafios.Literalura.service.ConsumeAPI;
import com.aluradesafios.Literalura.service.ConvierteDatos;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository repositorio;


    public Principal (AutorRepository repository) {this.repositorio = repository; }

    public void muestraElMenu() {
        int opcion = 0;
        boolean bucle = true;
        String menu = """
                                        
                    1 - Busca libro por titulo
                    2 - Lista de libros registrados
                    3 - Lista de autores registrados
                    4 - Lista de autores vivos en un año específico
                    5 - Lista de libros por idioma
                    6 - Lista de libros por autor
                    7 - Lista de los 10 libros más descargados registrados
                    0 - Salir
                    """;
        while (bucle) {
            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        mostrarLibrosRegistrados();
                        break;
                    case 3:
                        mostrarAutores();
                        break;
                    case 4:
                        mostrarLiveYearCheck();
                        break;
                    case 5:
                        mostrarMenuDeIdiomas();
                        break;
                    case 6:
                        buscarTitulosPorNombre();
                        break;
                    case 7:
                        mostrarMasDescargados();
                        break;
                    case 0:
                        bucle = false;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (Exception e) {
                System.out.println("Error, Intenta de nuevo." + e.getMessage());
                teclado.nextLine();
            }
        }
    }

    private void mostrarMasDescargados() {
        List<Libros> librosTemp = repositorio.masDescargados();
        librosTemp.forEach(System.out::println);
    }

    private void buscarTitulosPorNombre() {
        System.out.println("Escribe el nombre del autor");
        String nombreAutor = teclado.nextLine();
        List<Libros> librosTemp = repositorio.tituloPorAutor(nombreAutor);
        if (librosTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        librosTemp.forEach(System.out::println);
    }

    private void mostrarMenuDeIdiomas() {

        System.out.println("""
                es: Español
                pt: Portugués
                fr: Francés
                en: Inglés
                """);
        System.out.println("Ingresa el idioma que quieres buscar los libros:");
        String entrada = teclado.nextLine();
        List<Libros>  titulosTemp = repositorio.listaLibrosPorIdioma(entrada);
        if (titulosTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarAutores() {

        repositorio.findAll().forEach(System.out::println);

    }

    private void mostrarLiveYearCheck() {
        System.out.println("Ingresa el año específico en el que quieres buscar autores vivos");
        Integer year = teclado.nextInt();
        teclado.nextLine();

        List<Autor>  autorsTemp = repositorio.anoEspecificoAutorVivo(year);
        if (autorsTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        autorsTemp.forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        repositorio.todosLosLibros().forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException{
        System.out.println("Ingresa el nombre del libro que deseas buscar:");
        String nombreTitulo = teclado.nextLine();
        Datos datosConsulta = buscarLibro(nombreTitulo);
        Optional<Libros> libros = datosConsulta.resultados().stream()
                .map(l -> new Libros(
                        l.titulo(),
                        l.idioma(),
                        l.contadorDeDescargas(),
                        l.autores()
                )).
                findFirst();
        if (libros.isPresent()) {
            Libros librosTemp = libros.get();
            Autor autorTemp = librosTemp.getAutor();
//            if (repositorio.buscarTituloPorNombre(librosTemp.getTitulo()).isPresent()) {
//              System.out.println("No se puede registrar este libro más de una vez");}
             if (repositorio.findByNombreContainsIgnoreCase(autorTemp.getNombre()).isPresent()) {
                librosTemp.setAutor(repositorio.findByNombreContainsIgnoreCase(autorTemp.getNombre()).get());
                librosTemp.getAutor().addLibros(librosTemp);
                repositorio.save(librosTemp.getAutor());
                System.out.println(librosTemp);
            } else {
                libros.get().getAutor().addLibros(libros.get());
                repositorio.save(libros.get().getAutor());
                System.out.println(librosTemp);
            }

        } else {
            System.out.println("Titulo no encontrado");
        }

    }

    private Datos buscarLibro(String titulo) throws  InterruptedException {
        return conversor.obtenerDatos(new String(consumeAPI.obtenerDatos(URL_BASE+URLEncoder.encode(titulo,StandardCharsets.UTF_8))), Datos.class);
    }


}