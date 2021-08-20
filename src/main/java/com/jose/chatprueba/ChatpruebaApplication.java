package com.jose.chatprueba;

import com.jose.chatprueba.services.ChatServices;
import com.jose.chatprueba.services.IFicheroService;
import com.jose.chatprueba.services.MensajeServices;
import com.jose.chatprueba.services.UsuarioServices;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class ChatpruebaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatpruebaApplication.class, args);
    }
    //Ejecución del main
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    UsuarioServices usuarioServices;
    @Autowired
    ChatServices chatServices;
    @Autowired
    MensajeServices mensajeServices;
    @Autowired
    IFicheroService iFicheroService;
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    	return args -> {
    		System.out.println();
    		iFicheroService.init();
    	};
    }
    @Bean
    public ModelMapper modelMapper() {
    	return new ModelMapper();
    }
}
