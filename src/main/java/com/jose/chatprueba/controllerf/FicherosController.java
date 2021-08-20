package com.jose.chatprueba.controllerf;

import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.services.IFicheroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FicherosController {
	
	private final IFicheroService iFicheroService;
	
	@GetMapping(value="/files/{filename:.+}")
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		
		Resource file = iFicheroService.loadAsResource(filename);
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.getFile().toPath());
        } catch (IOException ex) {
            System.err.println("Ha habido un error determinando el tipo del fichero.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.body(file);
	}
}
