package br.com.sysdesc.pesquisa.repository.dao.impl;

import java.util.List;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;

import br.com.sysdesc.pesquisa.repository.dao.PesquisableDAO;
import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.dao.AbstractGenericDAO;
import br.com.sysdesc.util.dao.EntityPathUtil;

public abstract class PesquisableDAOImpl<T> extends AbstractGenericDAO<T> implements PesquisableDAO<T> {

	public PesquisableDAOImpl(EntityPath<T> entityPath, NumberPath<Long> id) {

		super(entityPath, id);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public List<T> pesquisar(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir,
			Integer rows) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = getClausule(selected, pesquisa, preFilter, pesquisaExibir);

		if (booleanBuilder.hasValue()) {
			query.where(booleanBuilder);
		}

		return query.orderBy(getCampoId().asc()).offset(rows.intValue()).limit(pesquisaExibir.getPaginacao())
				.list(getEntityPath());
	}

	public List<T> pesquisarTodos(boolean selected, String pesquisa, BooleanBuilder preFilter,
			Pesquisa pesquisaExibir) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = getClausule(selected, pesquisa, preFilter, pesquisaExibir);

		if (booleanBuilder.hasValue()) {
			query.where(booleanBuilder);
		}

		return query.orderBy(getCampoId().asc()).list(getEntityPath());
	}

	@Override
	public Long count(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = getClausule(selected, pesquisa, preFilter, pesquisaExibir);

		if (booleanBuilder.hasValue()) {
			query.where(booleanBuilder);
		}

		return query.count();
	}

	private BooleanBuilder getClausule(boolean selected, String pesquisa, BooleanBuilder preFilter,
			Pesquisa pesquisaExibir) {

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if (!StringUtil.isNullOrEmpty(pesquisa)) {

			pesquisaExibir.getPesquisaCampos().forEach(campo -> {

				BooleanExpression clausula = EntityPathUtil.getExpressionLike(getEntityPath(), selected, pesquisa,
						campo.getFlagTipoDado(), campo.getCampo());

				if (clausula != null) {

					booleanBuilder.or(clausula);
				}
			});

		}

		if (preFilter.hasValue()) {
			return preFilter.and(booleanBuilder);
		}

		return booleanBuilder;
	}

}