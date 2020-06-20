package br.com.sysdesc.pesquisa.service.impl;

import java.util.List;
import java.util.function.Function;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.repository.dao.PesquisableDAO;
import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.pesquisa.service.PesquisableService;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class AbstractPesquisableServiceImpl<T> implements PesquisableService<T> {

	private final PesquisableDAO<T> pesquisableDAO;

	private final Function<T, Long> id;

	public AbstractPesquisableServiceImpl(PesquisableDAO<T> pesquisableDAO, Function<T, Long> id) {
		this.pesquisableDAO = pesquisableDAO;
		this.id = id;
	}

	@Override
	public void salvar(T objetoPersistir) {

		pesquisableDAO.salvar(objetoPersistir);
	}

	@Override
	public void validar(T objetoPersistir) {
		// Método abstrato utilizado para efetuar validação de telas que necessitam.
	}

	@Override
	public Function<T, Long> getId() {
		return id;
	}

	@Override
	public T previows(Long valueId) {

		return validarObjeto(pesquisableDAO.previows(valueId));
	}

	@Override
	public T last() {

		return validarObjeto(pesquisableDAO.last());
	}

	@Override
	public T first() {

		return validarObjeto(pesquisableDAO.first());
	}

	@Override
	public T next(Long valueId) {

		return validarObjeto(pesquisableDAO.next(valueId));
	}

	@Override
	public T buscarPorId(Long id) {

		return validarObjeto(pesquisableDAO.obterPorId(id));
	}

	@Override
	public List<T> pesquisar(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir, Integer rows) {

		return pesquisableDAO.pesquisar(selected, pesquisa, preFilter, pesquisaExibir, rows);
	}

	@Override
	public List<T> pesquisarTodos(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir) {

		return pesquisableDAO.pesquisarTodos(selected, pesquisa, preFilter, pesquisaExibir);
	}

	@Override
	public Long count(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir) {

		return pesquisableDAO.count(selected, pesquisa, preFilter, pesquisaExibir);
	}

	private T validarObjeto(T objetoPesquisa) {

		if (objetoPesquisa == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_NENHUM_REGISTRO_ENCONTRADO);
		}

		return objetoPesquisa;
	}

	@Override
	public void invalidarObjeto() {

		pesquisableDAO.invalidarObjeto();
	}
}
