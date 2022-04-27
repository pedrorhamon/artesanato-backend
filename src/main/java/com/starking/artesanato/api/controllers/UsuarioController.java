package com.starking.artesanato.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.artesanato.api.dto.CredenciaisDTO;
import com.starking.artesanato.api.dto.TokenDTO;
import com.starking.artesanato.api.dto.UsuarioDTO;
import com.starking.artesanato.entity.Usuario;
import com.starking.artesanato.exception.ErroAcessoException;
import com.starking.artesanato.exception.RegraNegocioException;
import com.starking.artesanato.services.JwtService;
import com.starking.artesanato.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/usuarios")
@Api("Api de Usuário")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	private JwtService jwtService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
		this.usuarioService = usuarioService;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/autenticar")
	@ApiOperation("Autenticar Usuário")
	public ResponseEntity<?> autenticar( @RequestBody @Validated CredenciaisDTO dto ) {
		try {
			Usuario usuarioAutenticado = this.usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			String token = jwtService.gerarToken(usuarioAutenticado);
			TokenDTO tokenDTO = new TokenDTO( usuarioAutenticado.getNome(), token);
			return ResponseEntity.ok(tokenDTO);
		}catch (ErroAcessoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	@ApiOperation("Cadastrar Usuário")
	public ResponseEntity<?> salvar( @RequestBody @Validated UsuarioDTO dto ) {
		Usuario usuario = Usuario.builder()
					.nome(dto.getNome())
					.email(dto.getEmail())
					.senha(dto.getSenha())
					.celular(dto.getCelular())
					.cpf(dto.getCpf()).build();
		
		try {
			Usuario usuarioSalvo = this.usuarioService.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}	
	}
	
	@GetMapping("{id}")
	@ApiOperation("Buscar Usuário por ID")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
		Optional<Usuario> usuario = this.usuarioService.obterPorId(id);
		if(!usuario.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND );
		}
		return ResponseEntity.ok(usuario);
	}

}
