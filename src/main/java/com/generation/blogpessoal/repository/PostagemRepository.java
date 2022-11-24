package com.generation.blogpessoal.repository;


import com.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// dentro do genérico <> tem que passar dois parametros, sendo o primeiro o Model da Classe e o segundo a chave primária
//do Model, que no caso é o ID que foi definido como Long.
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

    public List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);

}
