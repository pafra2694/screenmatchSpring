package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.models.DatosEpisodio;
import com.aluracursos.screenmatch.models.DatosSerie;
import com.aluracursos.screenmatch.models.DatosTemporada;
import com.aluracursos.screenmatch.principal.EjemploStreams;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class  ScreenmatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}



}
