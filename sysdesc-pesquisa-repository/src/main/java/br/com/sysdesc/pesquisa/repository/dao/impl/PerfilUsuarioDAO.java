package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QPerfilUsuario.perfilUsuario;

import br.com.sysdesc.pesquisa.repository.model.PerfilUsuario;
import br.com.sysdesc.util.dao.AbstractGenericDAO;

public class PerfilUsuarioDAO extends AbstractGenericDAO<PerfilUsuario> {

	private static final long serialVersionUID = 1L;

	public PerfilUsuarioDAO() {
		super(perfilUsuario, perfilUsuario.codigoPerfil);
	}

}
