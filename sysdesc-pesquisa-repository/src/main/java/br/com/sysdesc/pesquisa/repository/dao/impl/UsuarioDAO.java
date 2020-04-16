package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QUsuario.usuario;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.repository.model.Usuario;
import br.com.sysdesc.util.classes.LongUtil;

public class UsuarioDAO extends PesquisableDAOImpl<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(usuario, usuario.idUsuario);
	}

	public Usuario obterLogin(String nomeUsuario, String md5) {

		return from().where(usuario.nomeUsuario.eq(nomeUsuario).and(usuario.senha.eq(md5))).singleResult(usuario);
	}

	public boolean verificarUsuarioJaCadastrado(Long idCliente, Long idUsuario) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(usuario.codigoCliente.eq(idCliente));

		if (!LongUtil.isNullOrZero(idUsuario)) {
			booleanBuilder.and(usuario.idUsuario.ne(idUsuario));
		}

		return from().where(booleanBuilder).exists();
	}

}
