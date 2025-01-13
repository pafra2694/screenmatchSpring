package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.models.Categoria;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

    //Derived query
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

//    List<Serie> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX (e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);
}
