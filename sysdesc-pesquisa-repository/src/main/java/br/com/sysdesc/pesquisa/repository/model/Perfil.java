package br.com.sysdesc.pesquisa.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_perfil")
@SequenceGenerator(name = "GEN_PERFIL", allocationSize = 1, sequenceName = "GEN_PERFIL")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_PERFIL")
	@Column(name = "id_perfil")
	private Long idPerfil;

	@Column(name = "tx_descricao")
	private String descricao;

}