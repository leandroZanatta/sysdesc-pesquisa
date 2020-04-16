package br.com.sysdesc.pesquisa.repository.dao;

import java.util.List;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.util.dao.GenericDAO;

public interface PesquisableDAO<T> extends GenericDAO<T> {

	public List<T> pesquisar(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir,
			Integer rows);

	public List<T> pesquisarTodos(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir);

	public Long count(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir);

}
