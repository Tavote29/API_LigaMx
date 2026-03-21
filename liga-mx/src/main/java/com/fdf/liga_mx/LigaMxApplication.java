package com.fdf.liga_mx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fdf.liga_mx.models.repositories.ClubRepository;
import com.fdf.liga_mx.models.repositories.EstadioRepository;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j

public class LigaMxApplication implements CommandLineRunner {

	private final EstadioRepository estadioRepository;
	private final ClubRepository clubRepository;

	
	
	public LigaMxApplication(EstadioRepository estadioRepository, ClubRepository clubRepository) {
		this.estadioRepository = estadioRepository;
		this.clubRepository = clubRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LigaMxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		short id = 1;
		var estadio = estadioRepository.findById(id).get();
		log.info(String.valueOf(estadio));
	}
	

}
