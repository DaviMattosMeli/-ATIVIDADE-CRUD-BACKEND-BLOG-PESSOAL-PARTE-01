package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    @GetMapping
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable Long id) {

        /*
        Forma mais "antiga de fazer
        Optional<Postagem> buscarPostagem = postagemRepository.findById(id);

        if(buscarPostagem.isPresent())
            return ResponseEntity.ok(buscarPostagem.get());
        else
            return ResponseEntity.notFound().build();*/

        return postagemRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getAll(@PathVariable String titulo) {
        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
        /*SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";*/
    }

    @PostMapping
    public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostagem(@PathVariable Long id) {
        try {
            postagemRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        //SEGUNDA FORMA DE FAZER
        // @DeleteMapping("/{id}")
        //    public ResponseEntity<Void> deletePostagem(@PathVariable Long id) {
        //        try {
        //            postagemRepository.deleteById(id);
        //            return ResponseEntity.status(204).build();
        //
        //        }catch (Exception e) {
        //            return ResponseEntity.notFound().build();
        //        }
        //    }
    }

    @PutMapping
    public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
        if (postagem.getId() == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postagemRepository.save(postagem));
    }
}
