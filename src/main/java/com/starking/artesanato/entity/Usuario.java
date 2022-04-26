package com.starking.artesanato.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table( name = "usuario" , schema = "artesanato")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name = "nome")
	@NotEmpty(message = "{campo.login.obrigatorio}")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	@JsonIgnore
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private String senha;
	
	@Column(name = "celular")
	private String celular;
	
	@Column(name = "cpf")
	@CPF
	private String cpf;
}
