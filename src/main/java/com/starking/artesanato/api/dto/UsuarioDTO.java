package com.starking.artesanato.api.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private String nome;
	private String cpf;
	private String email;
	private String celular;
	private String senha;
}
