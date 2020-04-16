package br.com.sysdesc.pesquisa.repository.dao.impl;

import static br.com.sysdesc.pesquisa.repository.model.QPesquisa.pesquisa;

import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.enumeradores.TipoPesquisaEnum;

public class PesquisaNormalDAO extends PesquisableDAOImpl<Pesquisa> {

	private static final long serialVersionUID = 1L;

	public PesquisaNormalDAO() {
		super(pesquisa, pesquisa.idPesquisa);
	}

	@Override
	public Pesquisa next(Long id) {

		if (LongUtil.isNullOrZero(id)) {
			return last();
		}

		Pesquisa objeto = from()
				.where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo()).and(pesquisa.idPesquisa.gt(id)))
				.orderBy(pesquisa.idPesquisa.asc()).limit(1L).singleResult(pesquisa);

		if (objeto == null) {
			return first();
		}

		return objeto;
	}

	@Override
	public Pesquisa previows(Long id) {

		if (LongUtil.isNullOrZero(id)) {
			return first();
		}

		Pesquisa objeto = from()
				.where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo()).and(pesquisa.idPesquisa.lt(id)))
				.orderBy(pesquisa.idPesquisa.desc()).limit(1L).singleResult(pesquisa);

		if (objeto == null) {
			return last();
		}

		return objeto;
	}

	@Override
	public Pesquisa last() {
		return from().where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())).orderBy(pesquisa.idPesquisa.desc())
				.limit(1L).singleResult(pesquisa);
	}

	@Override
	public Pesquisa first() {
		return from().where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())).orderBy(pesquisa.idPesquisa.asc())
				.limit(1L).singleResult(pesquisa);
	}

}
