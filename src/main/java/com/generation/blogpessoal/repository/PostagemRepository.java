package com.generation.blogpessoal.repository;


import com.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// dentro do genérico <> tem que passar dois parametros, sendo o primeiro o Model da Classe e o segundo a chave primária
//do Model, que no caso é o ID que foi definido como Long.
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

}
