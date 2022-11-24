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
    public ResponseEntity<List<Postagem>> getAll(){
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable Long id){
        /*Optional<Postagem> buscarPostagem = postagemRepository.findById(id);

        if(buscarPostagem.isPresent())
            return ResponseEntity.ok(buscarPostagem.get());
        else
            return ResponseEntity.notFound().build();*/

        return postagemRepository.findById(id)
                .map(resposta ->  ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
        
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getAll(@PathVariable String titulo){
        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
        /*SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";*/
    }

    @PostMapping
    public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
    }

    @DeleteMapping("/{id}")
    public void deletePostagem(@PathVariable Long id){
        postagemRepository.deleteById(id);
    }

    @PutMapping
    public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
    }


}
