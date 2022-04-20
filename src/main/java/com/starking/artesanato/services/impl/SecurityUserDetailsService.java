package com.starking.artesanato.services.impl;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.starking.artesanato.entity.Usuario;
import com.starking.artesanato.repositories.UsuarioRepository;
import com.starking.artesanato.utils.ConstanteUtils;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;

	public SecurityUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuarioEncontrado = usuarioRepository
				.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(ConstanteUtils.EMAIL_NAO_CADASTRADO));
		
		return User.builder()
				.username(usuarioEncontrado.getEmail())
				.password(usuarioEncontrado.getSenha())
				.roles("USER")
				.build();
	}

}
