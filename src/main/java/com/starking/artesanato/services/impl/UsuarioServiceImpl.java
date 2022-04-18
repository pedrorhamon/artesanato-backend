package com.starking.artesanato.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.starking.artesanato.entity.Usuario;
import com.starking.artesanato.exception.ErroAcessoException;
import com.starking.artesanato.exception.RegraNegocioException;
import com.starking.artesanato.repositories.UsuarioRepository;
import com.starking.artesanato.services.UsuarioService;
import com.starking.artesanato.utils.ConstanteUtils;

public class UsuarioServiceImpl implements UsuarioService{
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;
	
	@Autowired
	public UsuarioServiceImpl(
			UsuarioRepository usuarioRepository, 
			PasswordEncoder encoder) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.encoder = encoder;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAcessoException(ConstanteUtils.USUARIO_NAO_ENCONTRADO);
		}
		
		boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());
		
		if(!senhasBatem) {
			throw new ErroAcessoException(ConstanteUtils.SENHA_INVALIDA);
		}

		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		criptografarSenha(usuario);
		return usuarioRepository.save(usuario);
	}

	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = encoder.encode(senha);
		usuario.setSenha(senhaCripto);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = usuarioRepository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException(ConstanteUtils.USUARIO_CADASTRADO);
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return usuarioRepository.findById(id);
	}
}
