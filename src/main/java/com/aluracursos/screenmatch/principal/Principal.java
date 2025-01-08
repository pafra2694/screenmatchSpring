package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.models.DatosEpisodio;
import com.aluracursos.screenmatch.models.DatosSerie;
import com.aluracursos.screenmatch.models.DatosTemporada;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b7906e10&t";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){

        System.out.print("Por favor escribe el nombre de la serie que deseas buscar: ");
        //Busca los fatos generales de las series
        var nombreSerie = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") +"&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);

        //Mostrar solo el título de los episodios para las temporadas
        //for (int i = 0; i < datos.totalDeTemporadas(); i++) {
        //    List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
        //    for (int j = 0; j < episodiosTemporada.size(); j++) {
        //        System.out.println(episodiosTemporada.get(j).titulo());
        //    }
        //}

        //otra forma en una línea
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir toda la información a una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        /* ***************************************** TOP 5 EPISODIOS*******************************************************
        System.out.println("TOP 5 EPISODIOS");
        // Top 5 Episodios
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                //.peek(e -> System.out.println("Primer filtro (N/A)" + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                //.peek(e -> System.out.println("Segundo filtro ordenacion (M>m)" + e))
                .map(e -> e.titulo().toUpperCase())
                //.peek(e -> System.out.println("Tercer filtro pasar a mayúsculas"))
                .limit(5)
                .forEach(System.out::println);
        */


        //Convirtiendo los datos a una lista del tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        /* *********************************************** BÚSQUEDA POR AÑO *******************************************************
        // Busqueda de episodios a partir de x año
        System.out.print("Indica el año a partir del cual deseas ver los episodios:");
        var fecha = sc.nextInt();
        sc.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() + "\nEpisodio: " + e.getTitulo() + "\nFecha de lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
                ));
        */

        /* ***************************************** BÚSQUEDA POR PEDAZO DE NOMBRE DE EPISODIO *******************************************************
        //Busca episodios por pedazo de titulo
        System.out.print("Ingresa el nombre del episodio total o parcial que desea ver: ");
        var pedazoTitulo = sc.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase())) //se convierte a mayúsculas porque contains toma es case sensitive
                .findFirst();

        if(episodioBuscado.isPresent()){
            System.out.println("Episodio encontrado:");
            System.out.println(episodioBuscado);
        }else {
            System.out.println("Episodio no encontrado");
        }
        */

        //************************************* CALCULANDO EVALUACIONES POR TEMPORADA ******************************

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de las evaluaciones: " + est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluadio: " + est.getMin());


    }
}
