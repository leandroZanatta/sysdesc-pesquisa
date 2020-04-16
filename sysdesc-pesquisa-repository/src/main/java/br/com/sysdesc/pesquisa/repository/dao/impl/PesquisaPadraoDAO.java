package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QPesquisa.pesquisa;
import static br.com.sysdesc.pesquisa.repository.model.QPesquisaPadrao.pesquisaPadrao;

import com.mysema.query.jpa.impl.JPADeleteClause;

import br.com.sysdesc.pesquisa.repository.model.PesquisaPadrao;
import br.com.sysdesc.util.dao.AbstractGenericDAO;

public class PesquisaPadraoDAO extends AbstractGenericDAO<PesquisaPadrao> {

	private static final long serialVersionUID = 1L;

	public PesquisaPadraoDAO() {
		super(pesquisaPadrao, pesquisaPadrao.codigoPesquisa);
	}

	public void excluirPesquisaUsuario(Long codigoPesquisa, Long codigoUsuario) {

		getEntityManager().getTransaction().begin();

		new JPADeleteClause(getEntityManager(), pesquisaPadrao)
				.where(pesquisaPadrao.codigoUsuario.eq(codigoUsuario)
						.and(pesquisaPadrao.codigoPesquisa.in(subQuery().from(pesquisa)
								.where(pesquisa.codigoPesquisa.eq(codigoPesquisa)).list(pesquisa.idPesquisa))))
				.execute();

		getEntityManager().getTransaction().commit();
	}

}
