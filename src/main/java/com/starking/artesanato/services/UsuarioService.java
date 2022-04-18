package com.starking.artesanato.services;

import java.util.Optional;

import com.starking.artesanato.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	Usuario salvarUsuario(Usuario usuario);
	void validarEmail(String email);
	Optional<Usuario> obterPorId(Long id);
}
