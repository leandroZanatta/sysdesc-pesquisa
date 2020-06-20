package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QPerfil.perfil;

import br.com.sysdesc.pesquisa.repository.model.Perfil;

public class PerfilDAO extends PesquisableDAOImpl<Perfil> {

	private static final long serialVersionUID = 1L;

	public PerfilDAO() {
		super(perfil, perfil.idPerfil);
	}

}
