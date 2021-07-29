package com.jose.chatprueba;

import com.jose.chatprueba.services.ChatServices;
import com.jose.chatprueba.services.MensajeServices;
import com.jose.chatprueba.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
@EnableJpaRepositories
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
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    	return args -> {
    		System.out.println();
    	};
    }
}
