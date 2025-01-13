package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.models.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis
) {
}
