package com.aluradesafios.Literalura;

import com.aluradesafios.Literalura.Principal.Principal;
import com.aluradesafios.Literalura.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository repository;

	public static void main(String[] args)
	{
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}

}
