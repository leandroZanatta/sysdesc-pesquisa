package br.com.sysdesc.pesquisa.service;

import java.util.List;
import java.util.function.Function;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.repository.model.Pesquisa;

public interface PesquisableService<T> {

	public void validar(T objetoPersistir);

	public void salvar(T objetoPersistir);

	public T previows(Long valueId);

	public T next(Long valueId);

	public T last();

	public T first();

	public List<T> pesquisar(boolean selected, String text, BooleanBuilder preFilter, Pesquisa pesquisaExibir,
			Integer rows);

	public Long count(boolean selected, String text, BooleanBuilder preFilter, Pesquisa pesquisaExibir);

	public Function<T, Long> getId();

	public List<T> pesquisarTodos(boolean selected, String text, BooleanBuilder preFilter, Pesquisa pesquisaExibir);

	public T buscarPorId(Long id);

}
