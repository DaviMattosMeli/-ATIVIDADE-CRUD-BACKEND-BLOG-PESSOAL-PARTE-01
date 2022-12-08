package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", ""));
    }

    @Test
    @DisplayName("Cadastrar Usuario")
    public void deveCriarUmUsuario() {
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "paulo", "paulo@gmail.com", "123456789", "123"));

        ResponseEntity<Usuario> response = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        // faz uma comparação de criar um usuário, esperando nos dois objetos através da vírgula passado a mesma resposta.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), response.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), response.getBody().getUsuario());
    }

    @Test
    @DisplayName("Listar todos os Usuario")
    public void deveMostrarTodosUsuario() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Sabrina", "sabrina@gmail.com", "12345678", ""));
        usuarioService.cadastrarUsuario(new Usuario(0L, "Ricardo", "ricardo@gmail.com", "12345678", ""));

        //o <String> serve para listar apenas o status code
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/listartodos", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Login de um Usuario")
    public void Login() {

        HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<>(
                new UsuarioLogin("root@root.com", "rootroot"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/logar", HttpMethod.POST,
                corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }
}



