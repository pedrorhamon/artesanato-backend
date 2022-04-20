package com.starking.artesanato.api.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private String email;
	private String nome;
	private String senha;
}
