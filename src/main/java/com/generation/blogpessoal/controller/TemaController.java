package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class TemaController {

    @Autowired
    private TemaRepository temaRepository;

    @GetMapping
    public ResponseEntity<List<Tema>> getAll() {
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable Long id) {

        return temaRepository.findById(id)
                .map(res -> ResponseEntity.ok(res))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Tema>> getAll(@PathVariable String descricao) {
        if (temaRepository.findAllByDescricaoContainsIgnoreCase(descricao).isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(temaRepository.findAllByDescricaoContainsIgnoreCase(descricao));

    }

    @PostMapping
    public ResponseEntity<Tema> postPostagem(@Valid @RequestBody Tema tema) {
        if(tema.getId() == null)
        return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Tema> putPostagem(@Valid @RequestBody Tema tema) {
        if (tema.getDescricao() != null)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(temaRepository.save(tema));
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Tema> deletePostagem(@PathVariable Long id) {
        try {
            temaRepository.deleteById(id);
           return  ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
