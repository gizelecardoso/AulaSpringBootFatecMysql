package br.com.bossini.controller.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bossini.model.beans.Livro;
import br.com.bossini.repository.LivroRepository;

@RestController
@RequestMapping("/livros")
//o rest controller usa uma lib do jackson - que vai transformar nosso dado em um json
public class LivroResource {

	@Autowired
	private LivroRepository livroRepository;
	
	@GetMapping("/lista")
	public List<Livro> todosOsLivros() {
		return livroRepository.findAll();
	}
	
	@PostMapping("/salvar")
	//@ResponseStatus(HttpStatus.CREATED)//USANDO O RFC - REQUEST FOR COMMENT
	public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro, HttpServletResponse response) { //response enviada ao cliente, estamos injetando nesse Http, adicionando um novo header dando acesso a url
		//PRINCIPIOS REST - quando vc atende a uma req e da certo, e a req da uma resposta, vc da acesso a uma url para pegar o result
		
		try {
			
			Livro livroCriado = livroRepository.save(livro);
			
			//localhost:8080/livros/salvar                           id do livro                id do livro criado no banco
			URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/{id}").buildAndExpand(livroCriado.getId()).toUri();

			//response.setHeader("Location", uri.toASCIIString());

			return ResponseEntity.created(uri).body(livroCriado);
		}catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}
	
}
