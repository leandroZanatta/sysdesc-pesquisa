package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QPermissaoPesquisa.permissaoPesquisa;

import java.util.List;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPADeleteClause;

import br.com.sysdesc.pesquisa.repository.model.PermissaoPesquisa;
import br.com.sysdesc.util.classes.LongUtil;

public class PermissaoPesquisaDAO extends PesquisableDAOImpl<PermissaoPesquisa> {

	private static final long serialVersionUID = 1L;

	public PermissaoPesquisaDAO() {
		super(permissaoPesquisa, permissaoPesquisa.idPermissaopesquisa);
	}

	public List<PermissaoPesquisa> buscarPorPerfil(Long idPerfil) {

		return sqlFrom().where(permissaoPesquisa.codigoPerfil.eq(idPerfil)).list(permissaoPesquisa);
	}

	public List<PermissaoPesquisa> buscarPorUsuario(Long idUsuario) {

		return sqlFrom().where(permissaoPesquisa.codigoUsuario.eq(idUsuario)).list(permissaoPesquisa);
	}

	public void excluir(PermissaoPesquisa objeto) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(
				permissaoPesquisa.codigoPesquisa.eq(objeto.getCodigoPesquisa()));

		if (!LongUtil.isNullOrZero(objeto.getCodigoPerfil())) {

			booleanBuilder.and(permissaoPesquisa.codigoPerfil.eq(objeto.getCodigoPerfil()));
		}

		if (!LongUtil.isNullOrZero(objeto.getCodigoUsuario())) {

			booleanBuilder.and(permissaoPesquisa.codigoUsuario.eq(objeto.getCodigoUsuario()));
		}

		new JPADeleteClause(getEntityManager(), permissaoPesquisa).where(booleanBuilder).execute();
	}

}
