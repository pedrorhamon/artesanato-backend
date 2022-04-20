package com.starking.artesanato.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.artesanato.api.dto.TokenDTO;
import com.starking.artesanato.api.dto.UsuarioDTO;
import com.starking.artesanato.entity.Usuario;
import com.starking.artesanato.exception.ErroAcessoException;
import com.starking.artesanato.exception.RegraNegocioException;
import com.starking.artesanato.services.JwtService;
import com.starking.artesanato.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	private JwtService jwtService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
		this.usuarioService = usuarioService;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar( @RequestBody @Validated UsuarioDTO dto ) {
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
	public ResponseEntity<?> salvar( @RequestBody UsuarioDTO dto ) {
		Usuario usuario = Usuario.builder()
					.nome(dto.getNome())
					.email(dto.getEmail())
					.senha(dto.getSenha()).build();
		
		try {
			Usuario usuarioSalvo = this.usuarioService.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

}
